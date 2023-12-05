package org.ict.allaboutu.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.service.BoardDto;
import org.ict.allaboutu.notice.domain.Notice;
import org.ict.allaboutu.notice.repository.NoticeRepository;
import org.ict.allaboutu.notice.service.NoticeService;
import org.springframework.data.domain.Pageable;
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

//    @GetMapping("/search")
//    public ResponseEntity<List<Notice>> searchByTitle(@RequestParam String title) {
//        List<Notice> searchResults = noticeService.searchByTitle(title);
//
//        if (!searchResults.isEmpty()) {
//            return ResponseEntity.ok(searchResults);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/details/{noticeNum}")
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

    @PatchMapping("/{noticeNum}")
    public ResponseEntity<Notice> updateNotice(@PathVariable Long noticeNum, @RequestBody Notice updatedNotice) {
        Notice notice = noticeService.getByNoticeId(noticeNum);

        if (notice != null) {
            // 기존 공지사항의 필드를 updatedNotice의 값으로 업데이트
            notice.setNoticeTitle(updatedNotice.getNoticeTitle());
            notice.setNoticeContents(updatedNotice.getNoticeContents());
            // 업데이트하고자 하는 다른 필드 추가

            // 업데이트된 공지사항 저장
            noticeService.updateNotice(noticeNum, updatedNotice);

            return ResponseEntity.ok(notice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{noticeNum}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long noticeNum) {
        Notice existingNotice = noticeService.getByNoticeId(noticeNum);

        if (existingNotice != null) {
            noticeService.deleteNotice(noticeNum);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
