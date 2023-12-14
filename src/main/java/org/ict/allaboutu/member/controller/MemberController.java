package org.ict.allaboutu.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.member.domain.Member;
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

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class MemberController {

    private final MemberService memberService;
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
    public ResponseEntity<MemberDto> signup(
            @RequestBody Member member
    ) {
        log.info("/signup : " + member.toString());

        String rawPassword = member.getUserPwd();
        String encPassword = passwordEncoder.encode(rawPassword);
        member.setUserPwd(encPassword);

        return ResponseEntity.ok(memberService.signup(member));
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

//    /* 회원가입 */
//    @PostMapping("/auth/joinProc")
//    public String joinProc(@Valid UserRequestDto userDto, Errors errors, Model model) {
//        if (errors.hasErrors()) {
//            /* 회원가입 실패시 입력 데이터 값을 유지 */
//            model.addAttribute("userDto", userDto);
//
//            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
//            Map<String, String> validatorResult = userService.validateHandling(errors);
//            for (String key : validatorResult.keySet()) {
//                model.addAttribute(key, validatorResult.get(key));
//            }
//                /* 회원가입 페이지로 다시 리턴 */
//                return "/user/user-join";
//        }
//            userService.userJoin(userDto);
//            return "redirect:/auth/login";
//
//    }

}



