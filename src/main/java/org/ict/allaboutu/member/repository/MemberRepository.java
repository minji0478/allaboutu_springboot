package org.ict.allaboutu.member.repository;

import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.domain.ProfileHashtagLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT m.userId FROM Member m WHERE m.userNum = :userNum")
    public String findUserIdByUserNum(@Param("userNum") Long userNum);

    @Query("SELECT p FROM ProfileHashtagLink p JOIN ProfileHashtagLink phl ON p.hashtagNum = phl.hashtagNum WHERE phl.userNum = :userNum")
    public List<ProfileHashtagLink> findHashtagsByUserNum(@Param("userNum") Long userNum);
}
