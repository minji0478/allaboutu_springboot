package org.ict.allaboutu.board.repository;

import org.ict.allaboutu.board.domain.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<BoardLike, Long> {

    @Query("select l from BoardLike l where l.boardNum = :boardNum and l.userNum = :userNum")
    public BoardLike findByBoardNumAndUserNum(@Param("boardNum") Long boardNum, @Param("userNum") Long userNum) throws Exception;

    @Query("select max(l.likeNum) from BoardLike l")
    public Long findMaxLikeNum();

}
