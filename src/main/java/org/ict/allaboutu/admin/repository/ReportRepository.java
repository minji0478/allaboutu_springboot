package org.ict.allaboutu.admin.repository;

import org.ict.allaboutu.admin.domain.Report;
import org.ict.allaboutu.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report, Long> {

//    @Query("SELECT r.reportCount FROM Report r INNER JOIN Member u ON r.userNum = u.userNum")
//    List<Report> findAllByUserNum(@Param("userNum") Long userNum);

//    @Query("SELECT r.boardNum FROM Report r WHERE r.reportNum = :reportNum")
//    Report findByReportNum(@Param("reportNum") Long reportNum);

    @Query(value = "SELECT MAX(r.reportNum) FROM Report r")
    public Long findMaxReportNum();

    @Query("SELECT r.boardNum FROM Report r WHERE r.reportNum = :reportNum")
    Board findByReportNum(@Param("reportNum") Long reportNum);
}
