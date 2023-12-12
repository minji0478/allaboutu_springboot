package org.ict.allaboutu.myPage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.myPage.service.MyPageDto;
import org.ict.allaboutu.myPage.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MyPageController {

    private MyPageService myPageService;

    @GetMapping("/myPage/{userNum}")
    public ResponseEntity<MyPageDto> getMyPage(@PathVariable Long userNum){
        MyPageDto myPageDto = myPageService.getMyPage(userNum);
        return ResponseEntity.ok(myPageDto);
    }


    @PatchMapping("/myPage/{userNum}")
    public ResponseEntity<MyPageDto> updateUser(@PathVariable Long userNum, @RequestBody Member updateMember){
        MyPageDto myPageDto = myPageService.updateUser(userNum,updateMember);

        return ResponseEntity.ok(myPageDto);
    }

}
