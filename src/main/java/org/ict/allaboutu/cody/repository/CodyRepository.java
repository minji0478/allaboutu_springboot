package org.ict.allaboutu.cody.repository;

import org.ict.allaboutu.cody.domain.Cody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodyRepository extends JpaRepository<Cody, Long> {
}
