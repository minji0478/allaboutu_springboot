package org.ict.allaboutu.member.repository;

import org.ict.allaboutu.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepositoty extends JpaRepository<Member, Long> {

}
