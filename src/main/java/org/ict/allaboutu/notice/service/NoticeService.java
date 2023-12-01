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

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository  noticeRepository;

    public List<Notice> getNoticeList() {
        List<Notice> noticeList = noticeRepository.findAll();

        return noticeList;
    }

    public Notice getByNoticeId(Long noticeNum){
        return noticeRepository.findById(noticeNum).get();
    }

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



}