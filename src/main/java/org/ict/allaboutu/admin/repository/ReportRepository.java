package org.ict.allaboutu.admin.repository;

import org.ict.allaboutu.admin.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

//    @Query("SELECT r.reportCount FROM Report r INNER JOIN Member u ON r.userNum = u.userNum")
//    List<Report> findAllByUserNum(@Param("userNum") Long userNum);
}
