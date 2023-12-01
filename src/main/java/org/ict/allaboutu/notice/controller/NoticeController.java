package org.ict.allaboutu.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.service.BoardDto;
import org.ict.allaboutu.notice.domain.Notice;
import org.ict.allaboutu.notice.repository.NoticeRepository;
import org.ict.allaboutu.notice.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

       log.info("getNoticeList 결과 : " + list);

       return ResponseEntity.ok(list);
    }

    @GetMapping("/{noticeNum}")
    public ResponseEntity<Notice> getNotice(@PathVariable Long noticeNum) throws Exception {
        Notice notice = noticeService.getByNoticeId(noticeNum);

        if (notice != null) {
            return ResponseEntity.ok(notice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Notice> createNotice(@RequestBody Notice notice) throws Exception {
        return ResponseEntity.ok(noticeService.createNotice(notice));
    }


//    @PatchMapping
//    public Notice noticeUp(@RequestBody Notice notice) {
//        return noticeService.update(notice);
//    }

}
