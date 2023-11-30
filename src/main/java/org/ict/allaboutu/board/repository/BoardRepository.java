package org.ict.allaboutu.board.repository;

import org.ict.allaboutu.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT b FROM Board b ORDER BY b.boardNum DESC")
    public Page<Board> findAllByOrderByBoardNumDesc(Pageable pageable);

    @Query(value = "SELECT MAX(b.boardNum) FROM Board b")
    public Long findMaxBoardNum();

}
