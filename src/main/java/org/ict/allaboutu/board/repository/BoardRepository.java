package org.ict.allaboutu.board.repository;

import org.ict.allaboutu.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT b FROM Board b WHERE b.deleteDate IS NULL ORDER BY b.boardNum DESC")
    public Page<Board> findByDeleteDateIsNull(Pageable pageable);

    @Query(value = "SELECT MAX(b.boardNum) FROM Board b")
    public Long findMaxBoardNum();

    @Query(value = "SELECT COUNT(l) FROM BoardLike l WHERE l.boardNum = :boardNum")
    public Long countLikeByBoardNum(@Param("boardNum") Long boardNum);

    @Modifying
    @Query(value = "UPDATE Board b SET b.readCount = b.readCount + 1 WHERE b.boardNum = :boardNum")
    public void updateReadCount(@Param("boardNum") Long boardNum);

    @Query(value = "SELECT bc.category FROM BoardCategory bc WHERE bc.categoryNum = :categoryNum")
    public String findCategoryByCategoryNum(@Param("categoryNum") Long categoryNum);
}
