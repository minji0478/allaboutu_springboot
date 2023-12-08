package org.ict.allaboutu.website.repository;


import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.website.domain.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteRepository extends JpaRepository<Website, Long> {
}
