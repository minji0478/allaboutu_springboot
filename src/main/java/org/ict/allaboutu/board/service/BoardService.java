package org.ict.allaboutu.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.*;
import org.ict.allaboutu.board.repository.*;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.domain.ProfileHashtag;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@PropertySource("classpath:application.properties")
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final HashtagRepository hashtagRepository;
    private final BoardHashtagLinkRepository boardHashtagLinkRepository;
    private final MemberRepository memberRepository;
    private final AttachmentRepository attachmentRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Transactional
    public Page<BoardDto> getBoardList(Pageable pageable) {
        List<BoardDto> boardDtoList = new ArrayList<>();

        Page<Board> boardEntities = boardRepository.findByDeleteDateIsNull(pageable);

        Page<BoardDto> boardDtoPage = boardEntities.map(boardEntity -> {
            List<Comment> commentList = commentRepository.findAllByBoardNum(boardEntity.getBoardNum());
            List<CommentDto> comments = commentList.stream().map(comment -> {
                Member writer = memberRepository.findById(comment.getUserNum()).get();

                return CommentDto.builder()
                        .commentNum(comment.getCommentNum())
                        .boardNum(comment.getBoardNum())
                        .userNum(comment.getUserNum())
                        .userId(writer.getUserId())
                        .userName(writer.getUserName())
                        .content(comment.getContent())
                        .createDate(comment.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                        .modifyDate((comment.getModifyDate() != null) ? comment.getModifyDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) : "N/A")
                        .build();
            }).toList();

            List<BoardHashtag> hashtags = hashtagRepository.findAllByBoardNum(boardEntity.getBoardNum());

            boardRepository.updateReadCount(boardEntity.getBoardNum());
            boardEntity.setReadCount(boardEntity.getReadCount() + 1);

            Long likeCount = boardRepository.countLikeByBoardNum(boardEntity.getBoardNum());
            String category = boardRepository.findCategoryByCategoryNum(boardEntity.getCategoryNum());
            String userId = memberRepository.findUserIdByUserNum(boardEntity.getUserNum());
            Member member = memberRepository.findById(boardEntity.getUserNum()).get();

            return BoardDto.builder()
                    .boardNum(boardEntity.getBoardNum())
                    .userNum(boardEntity.getUserNum())
                    .userId(member.getUserId())
                    .userName(member.getUserName())
                    .categoryNum(boardEntity.getCategoryNum())
                    .category(category)
                    .boardTitle(boardEntity.getBoardTitle())
                    .boardContent(boardEntity.getBoardContent())
                    .createDate(boardEntity.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                    .modifyDate((boardEntity.getModifyDate() != null) ? boardEntity.getModifyDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) : "N/A")
                    .comments(comments)
                    .hashtags(hashtags)
                    .likeCount(likeCount)
                    .commentCount(comments.size())
                    .readCount(boardEntity.getReadCount())
                    .build();
        });

        return boardDtoPage;
    }

    public Board getBoardById(Long boardNum) throws Exception {

        return boardRepository.findById(boardNum).get();
    }

    public Board createBoard(BoardDto boardDto) {
        Board board = Board.builder()
                .boardNum(boardRepository.findMaxBoardNum() == null ? 1 : boardRepository.findMaxBoardNum() + 1)
                .userNum(boardDto.getUserNum())
                .categoryNum(boardDto.getCategoryNum())
                .boardTitle(boardDto.getBoardTitle())
                .boardContent(boardDto.getBoardContent())
                .boardTitle(boardDto.getBoardTitle())
                .boardContent(boardDto.getBoardContent())
                .createDate(LocalDateTime.now())
                .readCount(0L)
                .build();

        Board saveResult = boardRepository.save(board);

        for (BoardHashtag hashtag : boardDto.getHashtags()) {
            boolean exists = hashtagRepository.findByHashtag(hashtag.getHashtag()) != null ? true : false;
            if (!exists) { // 존재하지 않는 해시태그라면
                Long maxHashtagNum = hashtagRepository.findMaxHashtagNum();
                hashtag.setHashtagNum(maxHashtagNum == null ? 1 : maxHashtagNum + 1);
                hashtagRepository.save(hashtag);
            }

            BoardHashtagLink link = new BoardHashtagLink(new BoardHashtagLinkPK(board.getBoardNum(), hashtag.getHashtagNum()));
            boardHashtagLinkRepository.save(link);
        }

        return saveResult;
    }

    public void uploadImage(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        File destFile = new File(uploadDir + fileName);
        file.transferTo(destFile);
    }

    public void deleteBoard(Long boardNum) {
        Board board = boardRepository.findById(boardNum).get();
        board.setDeleteDate(LocalDateTime.now());
        boardRepository.save(board);
    }

}
