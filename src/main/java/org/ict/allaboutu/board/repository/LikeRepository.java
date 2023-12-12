package org.ict.allaboutu.board.repository;

import org.ict.allaboutu.board.domain.BoardLike;
import org.ict.allaboutu.board.domain.BoardLikePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<BoardLike, BoardLikePK> {
}
