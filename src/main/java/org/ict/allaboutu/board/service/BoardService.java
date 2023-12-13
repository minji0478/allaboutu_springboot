package org.ict.allaboutu.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.admin.domain.Report;
import org.ict.allaboutu.admin.repository.ReportRepository;
import org.ict.allaboutu.admin.service.ReportDto;
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

    @Transactional
    public BoardDto getBoardById(Long boardNum) throws Exception {
        Board board = boardRepository.findById(boardNum).get();
        return getBoardDto(board);
    }

    @Transactional
    public Page<BoardDto> searchBoard(String keyword, Pageable pageable) {
        Page<Board> boardPage = boardRepository.findByHashtag(keyword, pageable);

        System.out.println("boardPage: " + boardPage);

        Page<BoardDto> boardDtoPage = boardPage.map(board -> {
            return getBoardDto(board);
        });

        return boardDtoPage;
    }

    public BoardDto createBoard(BoardDto boardDto, List<String> hashtagList, List<MultipartFile> files) throws Exception {

        // Board 테이블 저장
        Long maxBoardNum = boardRepository.findMaxBoardNum();
        Long userNum = memberRepository.findByUserId(boardDto.getWriter().getUserId()).getUserNum();
        Board board = Board.builder()
                .boardNum(maxBoardNum == null ? 1 : maxBoardNum + 1)
                .userNum(userNum)
                .categoryNum(boardDto.getCategoryNum())
                .boardTitle(boardDto.getBoardTitle())
                .boardContent(boardDto.getBoardContent())
                .createDate(LocalDateTime.now())
                .readCount(0L)
                .build();
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
        boardDto = BoardDto.builder()
                .boardNum(savedBoard.getBoardNum())
                .userNum(savedBoard.getUserNum())
                .createDate(savedBoard.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .hashtags(boardHashtags)
                .attachments(attachments)
                .build();

        return boardDto;
    }

    public BoardDto updateBoard(Board board, List<String> hashtagList, List<MultipartFile> files) throws Exception {

        // Board 테이블 저장
        Board oldBoard = boardRepository.findById(board.getBoardNum()).get();
        board.setUserNum(oldBoard.getUserNum());
        board.setCategoryNum(oldBoard.getCategoryNum());
        board.setCreateDate(oldBoard.getCreateDate());
        board.setModifyDate(LocalDateTime.now());
        board.setReadCount(oldBoard.getReadCount());
        Board savedBoard = boardRepository.save(board);

        // BoardHashtagLink 테이블 업데이트
        List<BoardHashtag> boardHashtags = updateHashtag(hashtagList, board.getBoardNum());

        // Attachment 테이블 업데이트
        deleteAttachment(board.getBoardNum());
        List<Attachment> attachments = createAttachment(files, board.getBoardNum());

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

    public Report reportBoard(ReportDto reportDto) {
        Board board = boardRepository.findById(reportDto.getBoardNum()).get();
        Long reportUserNum = memberRepository.findByUserId(reportDto.getReportUserId()).getUserNum();
        Long maxReportNum = ReportRepository.findMaxReportNum();

        Report report = new Report();
        report.setReportNum(maxReportNum == null ? 1L : maxReportNum + 1L);
        report.setUserNum(board.getUserNum());
        report.setBoardNum(board.getBoardNum());
        report.setReportUserNum(reportUserNum);
        report.setReportCause(reportDto.getReportCause());
        report.setReportReason(reportDto.getReportReason());
        report.setDeleteDate(LocalDateTime.now());
        return ReportRepository.save(report);
    }

    @Transactional
    public Page<BoardDto> getBoardRank(Pageable pageable) {
        Page<Board> boardRankPage = boardRepository.findBestBoards(pageable);

        Page<BoardDto> boardRankDtoPage = boardRankPage.map(board -> {
            return getBoardDto(board);
        });

        for (int i = 0; i < boardRankDtoPage.getContent().size(); i++) {
            boardRankDtoPage.getContent().get(i).setRank(Long.valueOf(i + 1));
        }

        return boardRankDtoPage;
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

    @Transactional
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

    // 해시태그 업데이트
    public List<BoardHashtag> updateHashtag(List<String> hashtagList, Long boardNum) throws Exception {
        List<BoardHashtag> boardHashtags = new ArrayList<>();

        // hashtagList와 oldHashtags(기존 해시태그 리스트)를 비교하여,
        // 1. hashtagList와 oldHashtags에 모두 존재하는 해시태그는 건너뜀.
        // 2. hashtagList에만 존재하는 해시태그는 링크 테이블에 저장.
        // 3. oldHashtags에만 존재하는 해시태그는 링크 테이블에서 삭제.
        List<BoardHashtag> oldHashtags = hashtagRepository.findAllByBoardNum(boardNum);
        if (hashtagList != null) {
            for (String hashtag : hashtagList) {
                BoardHashtag boardHashtag = hashtagRepository.findByHashtag(hashtag);
                if (boardHashtag == null) {
                    // 존재하지 않는 해시태그라면 해시태그 생성 및 DB 저장
                    boardHashtag = new BoardHashtag();

                    Long maxHashtagNum = hashtagRepository.findMaxHashtagNum();
                    boardHashtag.setHashtagNum(maxHashtagNum == null ? 1 : maxHashtagNum + 1);
                    boardHashtag.setHashtag(hashtag);
                    hashtagRepository.save(boardHashtag);
                }
                boardHashtags.add(boardHashtag);

                if (oldHashtags != null) {
                    if (oldHashtags.contains(boardHashtag)) {
                        // 1. 기존에 존재하는 해시태그는 건너뜀
                        continue;
                    } else {
                        // 2. 새로 추가된 해시태그는 링크 테이블에 저장
                        BoardHashtagLinkPK linkPK = new BoardHashtagLinkPK(boardNum, boardHashtag.getHashtagNum());
                        BoardHashtagLink link = new BoardHashtagLink(linkPK);
                        boardHashtagLinkRepository.save(link);
                    }
                }
            }
        }

        // 3. 기존 해시태그 리스트에서 hashtagList에 없는 해시태그는 링크 테이블에서 삭제
        if (oldHashtags != null) {
            for (BoardHashtag oldHashtag : oldHashtags) {
                if (hashtagList == null || !hashtagList.contains(oldHashtag.getHashtag())) {
                    BoardHashtagLinkPK linkPK = new BoardHashtagLinkPK(boardNum, oldHashtag.getHashtagNum());
                    boardHashtagLinkRepository.deleteById(linkPK);
                }
            }
        }

        return boardHashtags;
    }

    // 첨부파일 테이블 등록
    public List<Attachment> createAttachment(List<MultipartFile> files, Long boardNum) throws Exception {
        List<Attachment> attachments = new ArrayList<>();

        if (files != null) {
            int idx = 0;
            for (MultipartFile file : files) {
                try {
                    // 파일 업로드
                    String renameFileName = uploadImage(file, idx);

                    // 업로드 성공 시 DB 저장
                    Attachment attachment = Attachment.builder()
                            .id(new AttachmentPK(boardNum, Long.valueOf(idx)))
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

        return attachments;
    }

    // 첨부파일 삭제
    public void deleteAttachment(Long boardNum) throws Exception {
        List<Attachment> oldAttachments = attachmentRepository.findAllByBoardNum(boardNum);

        if (oldAttachments != null) {
            for (Attachment oldAttachment : oldAttachments) {
                String savePath = System.getProperty("user.dir") + "/src/main/resources/board_upload/";
                File deleteFile = new File(savePath + oldAttachment.getRenameFileName());
                deleteFile.delete();
            }
        }
    }


}
