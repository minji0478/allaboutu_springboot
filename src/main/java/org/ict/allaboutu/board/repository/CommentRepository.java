package org.ict.allaboutu.board.repository;

import org.ict.allaboutu.board.domain.Comment;
import org.ict.allaboutu.board.service.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.boardNum = :boardNum order by c.commentNum desc")
    List<Comment> findAllByBoardNum(@Param("boardNum") Long boardNum);

    @Query("select max(c.commentNum) from Comment c")
    Long findMaxCommentNum();
}
