package org.ict.allaboutu.cody.repository;

import org.ict.allaboutu.cody.domain.Cody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CodyRepository extends JpaRepository<Cody, Long> {

    Page<Cody> findAllByFormNum(Pageable pageable, Long formNum);

    @Query(value = "SELECT COUNT(c) FROM Cody c WHERE c.formNum = :formNum")
    int countByStyleNum(@Param("formNum") Long formNum);

    @Query(value = "SELECT MAX(c.codyNum) FROM Cody c")
    Long findMaxCodyNum();

    @Query(value = "SELECT MAX(c.codyImgNum) FROM CodyImg c")
    Long findMaxCodyImgNum();

    @Query(value = "SELECT MAX(c.formNum) FROM Cody c")
    Long findMaxFormNum();

    Cody findByCodyNum(long codyNum);
}
