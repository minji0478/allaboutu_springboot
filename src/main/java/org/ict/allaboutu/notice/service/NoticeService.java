package org.ict.allaboutu.notice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.repository.BoardRepository;
import org.ict.allaboutu.board.repository.CommentRepository;
import org.ict.allaboutu.notice.domain.Notice;
import org.ict.allaboutu.notice.repository.NoticeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}