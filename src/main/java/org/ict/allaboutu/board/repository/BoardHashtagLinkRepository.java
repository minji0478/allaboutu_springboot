package org.ict.allaboutu.board.repository;

import org.ict.allaboutu.board.domain.BoardHashtagLink;
import org.ict.allaboutu.board.domain.BoardHashtagLinkPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardHashtagLinkRepository extends JpaRepository<BoardHashtagLink, BoardHashtagLinkPK> {
}
