package org.ict.allaboutu.admin.repository;

import org.ict.allaboutu.admin.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

//    @Query("SELECT r.reportCount FROM Report r INNER JOIN Member u ON r.userNum = u.userNum")
//    List<Report> findAllByUserNum(@Param("userNum") Long userNum);

    @Query("SELECT r FROM Report r WHERE r.boardNum = :boardNum")
    Optional<Report> findByReportNum(@Param("boardNum") Long boardNum);

    @Query(value = "SELECT MAX(r.reportNum) FROM Report r")
    public Long findMaxReportNum();
}
