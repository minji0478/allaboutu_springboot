package org.ict.allaboutu.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.notice.domain.Notice;
import org.ict.allaboutu.notice.service.NoticeDto;
import org.ict.allaboutu.notice.service.NoticeService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

//    @GetMapping
//    public ResponseEntity<Page<NoticeDto>> getNoticeList(@PageableDefault(sort = {"noticeNum"}) Pageable pageable) throws Exception {
//        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("noticeNum").descending());
//        Page<NoticeDto> list = noticeService.getNoticeList(pageable);
//        log.info("getNoticeList 결과 : " + list);
//        return ResponseEntity.ok(list);
//    }

    @GetMapping
    public ResponseEntity<Page<NoticeDto>> getNoticeList(@PageableDefault(sort = {"noticeNum"}) Pageable pageable) throws Exception {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("noticeNum").descending());
        Page<NoticeDto> list = noticeService.getNoticeList(pageable);
        log.info("getNoticeList 결과 : " + list);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/imp")   //필독공지
    public ResponseEntity<List<NoticeDto>> getImportantNotice() {
        List<NoticeDto> notices = noticeService.getImportantNotice();
        return ResponseEntity.ok(notices);
    }

    @GetMapping("/detail/{noticeNum}")
    public ResponseEntity<NoticeDto> getNotice(@PathVariable Long noticeNum) throws Exception {
        NoticeDto noticeDto = noticeService.getByNoticeId(noticeNum);
        if (noticeDto != null) {
            return ResponseEntity.ok(noticeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/image/{renamefileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String renamefileName) throws Exception {
        Resource resource = new ClassPathResource("/notice_upload/" + renamefileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(resource.getInputStream()));
    }

    @PostMapping
    public ResponseEntity<NoticeDto> createNotice(
            @RequestPart("notice") Notice notice,
            @RequestPart(value = "file", required = false) MultipartFile file) throws Exception {

        return ResponseEntity.ok(noticeService.createNotice(notice, file));

    }

    @PatchMapping("/{noticeNum}")
    public ResponseEntity<NoticeDto> updateNotice(
            @RequestPart("notice") Notice notice,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {
        return ResponseEntity.ok(noticeService.updateNotice(notice, file));

    }

    @DeleteMapping("/{noticeNum}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long noticeNum) {

        noticeService.deleteNotice(noticeNum);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<NoticeDto>> searchNoticesByKeyword(
            @RequestParam("searchType") String searchType,
            @RequestParam("keyword") String keyword,
            @PageableDefault(sort = {"noticeNum"}) Pageable pageable ) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("noticeNum").descending());
        return ResponseEntity.ok(noticeService.searchNoticesByKeyword(searchType, keyword , pageable));
    }
}