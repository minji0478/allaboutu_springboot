package org.ict.allaboutu.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.ict.allaboutu.member.service.MailDto;
import org.ict.allaboutu.member.service.MailService;
import org.ict.allaboutu.member.service.MemberDto;
import org.ict.allaboutu.member.service.MemberService;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;
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
        System.out.println("\n\nsignup - Member : " + member);
        log.info("/signup : " + member.toString());

        String rawPassword = member.getUserPwd();

        // 비밀번호 유효성 검사
        if (!isValidPassword(rawPassword)) {
            // 유효성 검사에 실패한 경우 에러 응답 반환 또는 예외 처리
            System.out.println("비밀번호 유효성 검사 실패 : " + rawPassword);
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

    @GetMapping("/member/findId")
    public ResponseEntity<String> findId(@RequestParam("userEmail") String userEmail) throws Exception {
        String userId = memberService.findIdByUserEmail(userEmail);

        System.out.println("userId length : " + userId.length());
        if (userId.length() <= 2) {
            throw new NoSuchElementException();
        } else {
            return ResponseEntity.ok(userId);
        }
    }

    @PostMapping("/member/findPwd")
    public ResponseEntity<LocalDateTime> findPwd(@RequestBody String infoStr) throws Exception {
        JSONObject infoJson = new JSONObject(infoStr);
        String userId = infoJson.get("userId").toString();
        String userEmail = infoJson.get("userEmail").toString();
        boolean exists = memberService.checkUserInfo(userId, userEmail);

        if (exists) {
            MailDto mailDto = mailService.generateTokenAndMail(userId, userEmail);
            return ResponseEntity.ok(mailDto.getIssuedAt());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/member/sendMail")
    public ResponseEntity<MailDto> sendMail(@RequestBody String jsonStr) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String userId = jsonObject.get("userId").toString();
        String userEmail = jsonObject.get("userEmail").toString();

        MailDto mailDto = mailService.sendMailWithToken(userId, userEmail);

        return ResponseEntity.ok(mailDto);
    }

    @PostMapping("/member/verifyCode")
    public ResponseEntity<Boolean> verifyCode(@RequestBody String verifyDataStr) throws Exception {
        JSONObject verifyDataJson = new JSONObject(verifyDataStr);
        String userId = verifyDataJson.get("userId").toString();
        String userEmail = verifyDataJson.get("userEmail").toString();
        String verificationCode = verifyDataJson.get("verificationCode").toString();

        boolean isCodeValid = mailService.verifyCode(userId, userEmail, verificationCode);

        return ResponseEntity.ok(isCodeValid);
    }

    @PatchMapping("/member/expireCode")
    public ResponseEntity<MailDto> expireCode(@RequestBody String jsonStr) throws Exception {
        JSONObject jsonObject = new JSONObject(jsonStr);
        String userId = jsonObject.get("userId").toString();
        String userEmail = jsonObject.get("userEmail").toString();

        MailDto mailDto = mailService.expireCode(userId, userEmail);

        return ResponseEntity.ok(mailDto);
    }

    @PatchMapping("/member/changePwd")
    public ResponseEntity<MemberDto> changePassword(@RequestBody String changePwdDataStr) throws Exception {
        JSONObject changePwdDataJson = new JSONObject(changePwdDataStr);
        String userId = changePwdDataJson.get("userId").toString();
        String userEmail = changePwdDataJson.get("userEmail").toString();
        String userPwd = changePwdDataJson.get("userPwd").toString();

        String encPassword = passwordEncoder.encode(userPwd);
        Member member = Member.builder()
                .userId(userId)
                .userEmail(userEmail)
                .userPwd(encPassword)
                .build();

        MemberDto memberDto = memberService.changePassword(member);

        return ResponseEntity.ok(memberDto);
    }


}
