package org.ict.allaboutu.myPage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.service.MemberDto;
import org.ict.allaboutu.member.service.MemberService;
import org.ict.allaboutu.myPage.service.MyPageDto;
import org.ict.allaboutu.myPage.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final MemberService memberService;
    @GetMapping("/myPage/{userId}")
    public ResponseEntity<MemberDto> getMyPage(@PathVariable String userId)throws Exception{
        userId =  URLDecoder.decode(userId, "UTF-8");
        System.out.println("userId : " + userId);
        MemberDto memberDto = memberService.getMember(userId);
        return ResponseEntity.ok(memberDto);
    }


    @PatchMapping("/myPage/{userId}")
    public ResponseEntity<MemberDto> updateUser(@PathVariable String userId, @RequestBody Member updateMember){
        MemberDto memberDto = myPageService.updateUser(userId,updateMember);

        return ResponseEntity.ok(memberDto);
    }

}
