package org.ict.allaboutu.personalcolor.repository;

import org.ict.allaboutu.personalcolor.domain.UserPersonalColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserpersonalColorRepository extends JpaRepository<UserPersonalColor, Long> {

    @Query("SELECT MAX(p.personalUserNum) FROM UserPersonalColor p")
    public Long findPersonalNum();
}
