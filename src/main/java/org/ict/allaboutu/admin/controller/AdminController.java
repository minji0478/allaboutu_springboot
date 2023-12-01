package org.ict.allaboutu.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.admin.service.AdminDto;
import org.ict.allaboutu.admin.service.AdminService;
import org.ict.allaboutu.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/admin")
    public ResponseEntity<Page<AdminDto>> getMemberList(
            @PageableDefault(sort = {"userNum"}) Pageable pageable
    ) throws Exception{
        pageable = PageRequest.of(0, 10, Sort.by("userNum").descending());
        Page<AdminDto> list = adminService.getMemberList(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/reports")
    public ResponseEntity<Page<AdminDto>> getReportList(
            @PageableDefault(sort = {"boardNum"}) Pageable pageable
    ) throws Exception{
        pageable = PageRequest.of(0, 10, Sort.by("boardNum").descending());
        Page<AdminDto> list = adminService.getReportList(pageable);
        return ResponseEntity.ok(list);
    }

//    @DeleteMapping("/reports/{boardNum}")
//    public void deleteReportBoard(@PathVariable Long boardNum) throws Exception {
//        adminService.deleteReportBoard(boardNum);
//    }

    @PatchMapping("/reports/{boardNum}")
    public ResponseEntity<Board> updateReportBoard(@PathVariable Long boardNum, @RequestBody Board board) {
        log.info("controller boardNum: " + boardNum);
        return adminService.updateReportBoard(boardNum, board);
    }
}

