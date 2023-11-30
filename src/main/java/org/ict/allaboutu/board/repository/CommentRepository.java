package org.ict.allaboutu.board.repository;

import org.ict.allaboutu.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.boardNum = :boardNum")
    List<Comment> findAllByBoardNum(@Param("boardNum") Long boardNum);

    @Query("select max(c.commentNum) from Comment c where c.boardNum = :boardNum")
    public Long findMaxCommentNum(@Param("boardNum") Long boardNum);
}
