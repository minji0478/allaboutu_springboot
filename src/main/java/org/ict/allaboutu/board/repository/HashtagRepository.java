package org.ict.allaboutu.board.repository;

import org.ict.allaboutu.board.domain.BoardHashtag;
import org.ict.allaboutu.board.domain.BoardHashtagLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagRepository extends JpaRepository<BoardHashtag, Long> {

    @Query("SELECT h FROM BoardHashtag h JOIN BoardHashtagLink bhl ON h.hashtagNum = bhl.id.hashtagNum WHERE bhl.id.boardNum = :boardNum")
    public List<BoardHashtag> findAllByBoardNum(@Param("boardNum") Long boardNum);

    @Query("SELECT h FROM BoardHashtag h WHERE h.hashtag = :hashtag")
    public BoardHashtag findByHashtag(@Param("hashtag") String hashtag);

    @Query("SELECT MAX(h.hashtagNum) FROM BoardHashtag h")
    public Long findMaxHashtagNum();

}
