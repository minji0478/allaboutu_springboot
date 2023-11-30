package org.ict.allaboutu.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.notice.domain.Notice;
import org.ict.allaboutu.notice.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notices")
public class NoticeController {

   private final NoticeService noticeService;
    @GetMapping
    public ResponseEntity<List<Notice>> getNoticeList() throws Exception {
        List<Notice> list = noticeService.getNoticeList();

       log.info("getBoardList 결과 : " + list);

       return ResponseEntity.ok(list);
    }

}
