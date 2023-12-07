package org.ict.allaboutu.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.admin.domain.Admin;
import org.ict.allaboutu.admin.service.AdminDto;
import org.ict.allaboutu.admin.service.AdminService;
import org.ict.allaboutu.board.domain.Board;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/admin")
    public ResponseEntity<List<AdminDto>> getMemberList() throws Exception{
//        pageable = PageRequest.of(0, 10, Sort.by("userNum").descending());
//        Page<AdminDto> list = adminService.getMemberList(pageable);
        List<AdminDto> list = adminService.getMemberList();
        return ResponseEntity.ok(list);
    }

//    @GetMapping("/reports")
//    public ResponseEntity<Page<AdminDto>> getReportList(@PageableDefault(sort = {"boardNum"}) Pageable pageable) throws Exception{
//        pageable = PageRequest.of(0, 10, Sort.by("boardNum").descending());
//        Page<AdminDto> list = adminService.getReportList(pageable);
//        return ResponseEntity.ok(list);
//    }

    @GetMapping("/reports")
    public ResponseEntity<List<AdminDto>> getReportList() throws Exception {
        List<AdminDto> list = adminService.getReportList();
        return ResponseEntity.ok(list);
    }

    @PatchMapping("/reports/{reportNum}")
    public Long updateReportBoard(@PathVariable Long reportNum) throws Exception {
        log.info("controller boardNum: " + reportNum);
        return adminService.updateReportBoard(reportNum);
    }

    @PatchMapping("/member/{userNum}")
    public ResponseEntity<Admin> updateMemberAccount(@PathVariable Long userNum, @RequestBody AdminDto adminDto) throws Exception{
        log.info("controllerUserNum : " +userNum);
//        Admin updateMember = adminService.updateMemberAccount(userNum, adminDto);
        Admin updateMember = adminService.updateMemberAccount(userNum, adminDto);
        return ResponseEntity.ok(updateMember);
    }

    @GetMapping("/reports/{boardNum}")
    public Board detailReportBoard(@PathVariable Long boardNum) throws Exception {
        return adminService.detailReportBoard(boardNum);
    }
}
