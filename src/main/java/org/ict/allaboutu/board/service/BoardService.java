package org.ict.allaboutu.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.admin.domain.Report;
import org.ict.allaboutu.admin.repository.ReportRepository;
import org.ict.allaboutu.board.domain.*;
import org.ict.allaboutu.board.repository.*;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.domain.ProfileHashtag;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.ict.allaboutu.member.repository.ProfileHashtagRepository;
import org.ict.allaboutu.member.service.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final HashtagRepository hashtagRepository;
    private final BoardHashtagLinkRepository boardHashtagLinkRepository;
    private final MemberRepository memberRepository;
    private final AttachmentRepository attachmentRepository;
    private final ReportRepository ReportRepository;
    private final ProfileHashtagRepository profileHashtagRepository;

    @Transactional
    public Page<BoardDto> getBoardList(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findByDeleteDateIsNull(pageable);

        Page<BoardDto> boardDtoPage = boardPage.map(board -> {
            return getBoardDto(board);
        });

        return boardDtoPage;
    }

    public Board getBoardById(Long boardNum) throws Exception {
        return boardRepository.findById(boardNum).get();
    }

    public BoardDto createBoard(Board board, List<String> hashtagList, List<MultipartFile> files) throws Exception {

        // Board 테이블 저장
        Long maxBoardNum = boardRepository.findMaxBoardNum();
        board.setBoardNum(maxBoardNum == null ? 1 : maxBoardNum + 1);
        board.setCreateDate(LocalDateTime.now());
        board.setReadCount(0L);
        Board savedBoard = boardRepository.save(board);

        // BoardHashtag 테이블 저장
        List<BoardHashtag> boardHashtags = new ArrayList<>();
        if (hashtagList != null) {
            for (String hashtag : hashtagList) {
                BoardHashtag boardHashtag = hashtagRepository.findByHashtag(hashtag);
                if (boardHashtag == null) { // 존재하지 않는 해시태그라면
                    boardHashtag = new BoardHashtag();

                    Long maxHashtagNum = hashtagRepository.findMaxHashtagNum();
                    boardHashtag.setHashtagNum(maxHashtagNum == null ? 1 : maxHashtagNum + 1);
                    boardHashtag.setHashtag(hashtag);
                    hashtagRepository.save(boardHashtag);
                }

                BoardHashtagLink link = new BoardHashtagLink(new BoardHashtagLinkPK(board.getBoardNum(), boardHashtag.getHashtagNum()));
                boardHashtagLinkRepository.save(link);

                boardHashtags.add(boardHashtag);
            }
        }

        // Attachment 테이블 저장
        List<Attachment> attachments = new ArrayList<>();
        if (files != null) {
            int idx = 0;
            for (MultipartFile file : files) {
                try {
                    // 파일 업로드
                    String renameFileName = uploadImage(file, idx);

                    // 업로드 성공 시 DB 저장
                    Attachment attachment = Attachment.builder()
                            .id(new AttachmentPK(board.getBoardNum(), Long.valueOf(idx)))
                            .originalFileName(file.getOriginalFilename())
                            .renameFileName(renameFileName)
                            .build();

                    attachmentRepository.save(attachment);

                    attachments.add(attachment);
                    idx++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // BoardDto 객체 생성
        BoardDto boardDto = BoardDto.builder()
                .boardNum(savedBoard.getBoardNum())
                .userNum(savedBoard.getUserNum())
                .categoryNum(savedBoard.getCategoryNum())
                .boardTitle(savedBoard.getBoardTitle())
                .boardContent(savedBoard.getBoardContent())
                .hashtags(boardHashtags)
                .attachments(attachments)
                .build();

        return boardDto;
    }

    public String uploadImage(MultipartFile file, int index) throws Exception {
        String originalFileName = file.getOriginalFilename();
        String renameFileName = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis()));
        renameFileName += "_" + index + "." + originalFileName.substring(
                originalFileName.lastIndexOf(".") + 1);

        String savePath = System.getProperty("user.dir") + "/src/main/resources/board_upload/";
        File saveFile = new File(savePath, renameFileName);
        file.transferTo(saveFile);

        return renameFileName;
    }

    public void deleteBoard(Long boardNum) {
        Board board = boardRepository.findById(boardNum).get();
        board.setDeleteDate(LocalDateTime.now());
        boardRepository.save(board);
    }

    public Report reportBoard(Report report) {
        Board board = boardRepository.findById(report.getBoardNum()).get();
        Long maxReportNum = ReportRepository.findMaxReportNum();
        report.setReportNum(maxReportNum == null ? 1 : maxReportNum + 1);
        report.setUserNum(board.getUserNum());
        report.setDeleteDate(LocalDateTime.now());
        return ReportRepository.save(report);
    }

    public Page<BoardDto> getBoardRank(Pageable pageable) {
        Page<Board> boardRankPage = boardRepository.findBestBoards(pageable);

        Page<BoardDto> bpardRankDtoPage = boardRankPage.map(board -> {
            return getBoardDto(board);
        });

        return bpardRankDtoPage;
    }

    public MemberDto getMemberDto(Long userNum) {
        Member writer = memberRepository.findById(userNum).get();
        List<ProfileHashtag> profileHashtags = profileHashtagRepository.findAllByUserNum(userNum);

        MemberDto writerDto = MemberDto.builder()
                .userNum(writer.getUserNum())
                .userId(writer.getUserId())
                .userName(writer.getUserName())
                .userProfile(writer.getUserProfile())
                .hashtags(profileHashtags)
                .build();

        return writerDto;
    }

    public BoardDto getBoardDto(Board board) {
        List<Comment> commentList = commentRepository.findAllByBoardNum(board.getBoardNum());
        List<CommentDto> comments = commentList.stream().map(comment -> {
            MemberDto writerDto = getMemberDto(comment.getUserNum());

            return CommentDto.builder()
                    .commentNum(comment.getCommentNum())
                    .boardNum(comment.getBoardNum())
                    .userNum(comment.getUserNum())
                    .writer(writerDto)
                    .content(comment.getContent())
                    .createDate(comment.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .modifyDate((comment.getModifyDate() != null) ? comment.getModifyDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "N/A")
                    .build();
        }).toList();

        // 해시태그 및 첨부파일 목록 조회
        List<BoardHashtag> hashtags = hashtagRepository.findAllByBoardNum(board.getBoardNum());
        List<Attachment> attachments = attachmentRepository.findAllByBoardNum(board.getBoardNum());

        // 조회수 증가
        boardRepository.updateReadCount(board.getBoardNum());
        board.setReadCount(board.getReadCount() + 1);

        Long likeCount = boardRepository.countLikeByBoardNum(board.getBoardNum());
        String category = boardRepository.findCategoryByCategoryNum(board.getCategoryNum());
        MemberDto writerDto = getMemberDto(board.getUserNum());

        return BoardDto.builder()
                .boardNum(board.getBoardNum())
                .userNum(board.getUserNum())
                .writer(writerDto)
                .categoryNum(board.getCategoryNum())
                .category(category)
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .createDate(board.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .modifyDate((board.getModifyDate() != null) ? board.getModifyDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "N/A")
                .comments(comments)
                .hashtags(hashtags)
                .attachments(attachments)
                .likeCount(likeCount)
                .commentCount(Long.valueOf(comments.size()))
                .readCount(board.getReadCount())
                .build();
    }

}
