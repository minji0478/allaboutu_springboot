package org.ict.allaboutu.Access;

import lombok.RequiredArgsConstructor;
import org.ict.allaboutu.board.domain.BoardLike;
import org.ict.allaboutu.board.service.*;
import org.ict.allaboutu.member.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessService {
    private final MemberService memberService;
    private final BoardService boardService;
    private final LikeService likeService;
    private final CommentService commentService;

    public boolean isBoardAuthor(Authentication authentication, Long boardNum) throws Exception {
        // 게시글 작성자 정보 가져오기
        BoardDto boardDto = boardService.getBoardById(boardNum);

        // 현재 인증된 사용자 정보 가져오기
        String currentUser = authentication.getName();

        // 게시글 작성자와 현재 사용자가 일치하는지 확인
        return currentUser.equals(boardDto.getWriter().getUserId());
        //return isWriter(currentUser, boardDto.getWriter().getUserId());
    }

    public boolean isLikeOwner(Authentication authentication, Long boardNum, Long userNum) throws Exception {
        // 좋아요를 누른 사용자 정보 가져오기
        BoardLike isLiked = likeService.isLiked(boardNum, userNum);

        // 현재 인증된 사용자 정보 가져오기
        String currentId = authentication.getName();
        Long currentUserNum = memberService.getMember(currentId).getUserNum();
        // 좋아요를 누른 사용자와 현재 사용자가 일치하는지 확인
        return currentUserNum.equals(isLiked.getId().getUserNum());
    }

    public boolean isCommentAuthor(Authentication authentication, Long boardNum, Long commentNum) throws Exception {
        // 댓글 작성자 정보 가져오기
        CommentDto commentDto = commentService.getComment(commentNum);

        // 현재 인증된 사용자 정보 가져오기
        String currentId = authentication.getName();

        // 댓글 작성자와 현재 사용자가 일치하는지 확인
        return currentId.equals(commentDto.getWriter().getUserId());
    }

}
