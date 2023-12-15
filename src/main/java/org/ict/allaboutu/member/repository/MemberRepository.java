package org.ict.allaboutu.member.repository;

import org.ict.allaboutu.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT m.userId FROM Member m WHERE m.userNum = :userNum")
    public String findUserIdByUserNum(@Param("userNum") Long userNum);

    Member findByUserId(String userId);

    @Query(value = "SELECT MAX(m.userNum) FROM Member m")
    Long findMaxUserNum();

    Member findByUserEmail(String userEmail);

}
