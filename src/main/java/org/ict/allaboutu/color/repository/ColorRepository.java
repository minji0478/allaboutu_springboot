package org.ict.allaboutu.color.repository;

import org.ict.allaboutu.color.domain.Color;
import org.ict.allaboutu.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
}
