package org.ict.allaboutu.member.repository;

import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.domain.ProfileHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileHashtagRepository extends JpaRepository<ProfileHashtag, Long> {

    @Query("select p from ProfileHashtag p join ProfileHashtagLink phl on p.hashtagNum = phl.id.hashtagNum where phl.id.userNum = :userNum")
    List<ProfileHashtag> findAllByUserNum(@Param("userNum") Long userNum);
}
