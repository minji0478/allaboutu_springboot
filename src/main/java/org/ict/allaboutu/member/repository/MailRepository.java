package org.ict.allaboutu.member.repository;

import org.ict.allaboutu.member.service.MailDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MailRepository extends JpaRepository<MailDto, Long> {
    @Query(value = "SELECT MAX(m.mailNum) FROM MailDto m")
    Optional<Long> findMaxMailNum();

    List<MailDto> findByUserIdAndUserEmail(String userId, String userEmail);
}
