package org.ict.allaboutu.myPage.repository;

import org.ict.allaboutu.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyPageRepository extends JpaRepository<Member, Long> {

//    Member findByUserId(Long userId);
}
