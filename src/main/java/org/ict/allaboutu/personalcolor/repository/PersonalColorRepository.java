package org.ict.allaboutu.personalcolor.repository;

import org.ict.allaboutu.personalcolor.domain.PersonalColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalColorRepository extends JpaRepository<PersonalColor, Long> {

}
