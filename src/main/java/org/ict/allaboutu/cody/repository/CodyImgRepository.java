package org.ict.allaboutu.cody.repository;

import org.ict.allaboutu.cody.domain.CodyImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodyImgRepository extends JpaRepository<CodyImg, Long> {
    @Query("select c from CodyImg c where c.codyNum = :codyNum order by c.codyImgNum asc")
    List<CodyImg> findAllByCodyNum(@Param("codyNum") Long codyNum);
}
