package org.ict.allaboutu.notice.repository;

import org.ict.allaboutu.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query(value="select MAX (n.noticeNum) from Notice n")
    public Long findMaxNoticeNumber(    ); // 노티스 테이블에서 노티스 넘의 최대값을 셀렉트 가져온다.
}
