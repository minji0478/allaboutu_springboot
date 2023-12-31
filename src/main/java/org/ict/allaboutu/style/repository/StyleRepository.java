package org.ict.allaboutu.style.repository;

import org.ict.allaboutu.style.domain.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleRepository extends JpaRepository<Style, Long>{
    @Query(value = "SELECT MAX(s.styleNum) FROM Style s")
    public Long findMaxStyleNum();
}
