package org.ict.allaboutu.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.ict.allaboutu.member.service.MailDto;
import org.ict.allaboutu.member.service.MemberDto;
import org.ict.allaboutu.member.service.MemberService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/member/image/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws Exception {
        Resource resource = new ClassPathResource("/user_profile/" + imageName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(resource.getInputStream()));
    }

    @GetMapping("/member/{userId}")
    public ResponseEntity<MemberDto> getMember(
            @PathVariable String userId
    ) {
        log.info("/member/{userId} : " + userId);
        return ResponseEntity.ok(memberService.getMember(userId));
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberDto> signup(@RequestBody Member member) {
        log.info("/signup : " + member.toString());

        String rawPassword = member.getUserPwd();

        // 비밀번호 유효성 검사
        if (!isValidPassword(rawPassword)) {
            // 유효성 검사에 실패한 경우 에러 응답 반환 또는 예외 처리
            return ResponseEntity.badRequest().build();
        }

        String encPassword = passwordEncoder.encode(rawPassword);
        member.setUserPwd(encPassword);

        return ResponseEntity.ok(memberService.signup(member));
    }

    private boolean isValidPassword(String password) {
        // 비밀번호의 유효성 검사 로직을 추가
        // 여기서는 8~16자의 길이, 영문 대소문자, 숫자, 특수문자가 각각 1개 이상 포함되어 있는지 확인합니다.
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$";
        return password != null && password.matches(passwordPattern);
    }

    @PostMapping("/member/findId")
    public ResponseEntity<Void> findId(
            @RequestParam("email") String userEmail
    ) {
        System.out.println("findId - userEmail : " + userEmail);
        MailDto dto = memberService.createMailForId(userEmail);

        memberService.mailSend(dto);
        return ResponseEntity.noContent().build();
    }

}



