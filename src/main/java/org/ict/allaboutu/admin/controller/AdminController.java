package org.ict.allaboutu.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.admin.service.AdminDto;
import org.ict.allaboutu.admin.service.AdminService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class AdminController {
    private final AdminService adminService;

//    @GetMapping
//    public ResponseEntity<List<AdminDto>> getMemberList(Pageable pageable) throws Exception {
//        List<AdminDto> list = (List<AdminDto>) adminService.getMemberList();
//
//        log.info("MemberList : " + list);
//
//        return ResponseEntity.ok(list);
//
//    }

    @GetMapping("/members")
    public List<AdminDto> memberList(){
        return adminService.getMemberList();
    }
}

