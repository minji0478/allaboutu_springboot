package org.ict.allaboutu.myPage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.myPage.service.MyPageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MyPageController {

    private MyPageService myPageService;

//    @GetMapping("/{userId}")
//    public ResponseEntity<MyPageDto> getMyPage(@PathVariable Long userId){
//        MyPageDto myPageDto = myPageService.getMyPage(userId);
//        return ResponseEntity.ok(myPageDto);
//    }


//    @PatchMapping("/website/{userId}") //나중에 (website) myPage로 바꿔줘야함
//    public ResponseEntity<MyPageDto> updateUser(@PathVariable Long userId, @RequestBody Member updateMember){
//        MyPageDto myPageDto = myPageService.updateUser(userId).get();
//
//        // 수정할 필드만 업데이트
//        member.setUserPwd(updateMember.getUserPwd());
//        member.setUserPhone(updateMember.getUserPhone());
//
//        memberRepository.save(member);
//        return ResponseEntity.ok(member);
//    }

}
