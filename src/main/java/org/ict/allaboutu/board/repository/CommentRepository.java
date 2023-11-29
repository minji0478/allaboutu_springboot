package org.ict.allaboutu.board.repository;

import org.ict.allaboutu.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
