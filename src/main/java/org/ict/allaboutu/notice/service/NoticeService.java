package org.ict.allaboutu.notice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.repository.BoardRepository;
import org.ict.allaboutu.board.repository.CommentRepository;
import org.ict.allaboutu.board.service.BoardDto;
import org.ict.allaboutu.notice.domain.Notice;
import org.ict.allaboutu.notice.repository.NoticeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository  noticeRepository;

    public List<Notice> getNoticeList() {
            List<Notice> noticeList = noticeRepository.findAll();

        return noticeList;
    }

//    public List<Notice> searchByTitle(String title) {
//        return noticeRepository.(title);
//    }

    public Notice createNotice(Notice notice) {

        Long noticeNum = noticeRepository.findMaxNoticeNumber() + 1;
        notice.setNoticeNum(noticeNum);
        notice.setWriteDate(Date.valueOf(LocalDate.now()));
        notice.setReadCount(0L);

        return  noticeRepository.save(notice);

    }
    public Notice noticeUp(@RequestBody Notice notice){
        return noticeRepository.save(notice);
    }


    public Notice getByNoticeId(Long noticeNum) {
        Optional<Notice> optionalNotice = noticeRepository.findById(noticeNum);
        return optionalNotice.orElse(null); // 또는 필요한 경우 사용자 정의 예외를 throw
    }

    public Notice updateNotice(Long noticeNum, Notice updatedNotice) {
        // 확인: 해당 noticeNum의 공지사항이 존재하는지 확인
        Notice notice = noticeRepository.findById(noticeNum).orElse(null);
        if (notice == null) {
            // 존재하지 않으면 null 반환 또는 예외 처리
            return null;
        }

        // 필요한 필드를 업데이트
        notice.setNoticeTitle(updatedNotice.getNoticeTitle());
        notice.setNoticeContents(updatedNotice.getNoticeContents());
        // 업데이트하고자 하는 다른 필드 추가

        // 업데이트된 공지사항 저장
        return noticeRepository.save(notice);
    }

    public void deleteNotice(Long noticeNum) {
            // 확인: 해당 noticeNum의 공지사항이 존재하는지 확인
        Notice notice = noticeRepository.findById(noticeNum).orElse(null);
        if (notice != null) {
            // 존재하면 삭제
            noticeRepository.delete(notice);
        }
        // 존재하지 않아도 아무런 동작을 하지 않음 (에러를 발생시키거나 예외를 던지는 등의 처리도 가능)
    }




}