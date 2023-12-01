package org.ict.allaboutu.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Comment;
import org.ict.allaboutu.board.repository.CommentRepository;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    public List<CommentDto> getCommentList(Long boardNum) throws Exception {
        List<CommentDto> list = new ArrayList<>();

        List<Comment> commentList = commentRepository.findAllByBoardNum(boardNum);
        for (Comment comment : commentList) {
            Member writer = memberRepository.findById(comment.getUserNum()).get();

            CommentDto commentDto = CommentDto.builder()
                    .commentNum(comment.getCommentNum())
                    .boardNum(comment.getBoardNum())
                    .userNum(comment.getUserNum())
                    .userId(writer.getUserId())
                    .userName(writer.getUserName())
                    .parentNum(comment.getParentNum())
                    .content(comment.getContent())
                    .createDate(comment.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                    .modifyDate((comment.getModifyDate() != null) ? comment.getModifyDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) : "N/A")
                    .build();

            list.add(commentDto);
        }

        return list;
    }

    public CommentDto createComment(Long boardNum, CommentDto commentDto) throws Exception {
        Comment comment = Comment.builder()
                .commentNum(commentRepository.findMaxCommentNum() + 1)
                .boardNum(commentDto.getBoardNum())
                .userNum(commentDto.getUserNum())
                .parentNum(commentDto.getParentNum())
                .content(commentDto.getContent())
                .createDate(LocalDateTime.now())
                .build();

        commentRepository.save(comment);

        return commentDto;
    }

    public CommentDto updateComment(Long boardNum, Long commentNum, CommentDto commentDto) throws Exception {
        Comment comment = commentRepository.findById(commentNum).get();
        comment.setContent(commentDto.getContent());
        comment.setModifyDate(LocalDateTime.now());

        commentRepository.save(comment);

        commentDto.setModifyDate(comment.getModifyDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));

        return commentDto;
    }

    public void deleteComment(Long commentNum) throws Exception {
        commentRepository.deleteById(commentNum);
    }

}
