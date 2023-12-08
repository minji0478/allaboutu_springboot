package org.ict.allaboutu.notice.repository;

import org.ict.allaboutu.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query(value="select MAX (n.noticeNum) from Notice n")
    public Long findMaxNoticeNumber(    ); // 노티스 테이블에서 노티스 넘의 최대값을 셀렉트 가져온다.

    @Query("SELECT n FROM Notice n WHERE n.importance = 'Y' AND n.importanceDate >= CURRENT_DATE  ORDER BY n.noticeNum DESC")
    List<Notice> findImportantNotice();

    @Query("SELECT n FROM Notice n WHERE LOWER(n.noticeTitle) LIKE LOWER(CONCAT('%', :title, '%'))")
    public List<Notice> searchByTitle(@Param("title") String title);

    @Modifying
    @Query(value = "UPDATE Notice n set n.readCount = n.readCount + 1 where n.noticeNum = :noticeNum")
    void updateReadCount(@Param("noticeNum") Long noticeNum);


}
