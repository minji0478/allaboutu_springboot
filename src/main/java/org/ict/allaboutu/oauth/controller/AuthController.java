package org.ict.allaboutu.oauth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.oauth.service.AuthService;
import org.ict.allaboutu.oauth.vo.AuthRequest;
import org.ict.allaboutu.oauth.vo.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest login) {

        Member member = Member.builder()
                .userId(login.userId())
                .userPwd(login.userPwd())
                .build();
        System.out.println("\n\nlogin 테스트 1 : " + member.getUserId());
        System.out.println("\n\nlogin 테스트 1 : " + member.getUserPwd());

        return ResponseEntity.ok(authService.authenticate(member));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

    @GetMapping("/social/{socialType}")
    public ResponseEntity<Member> socialLogin(
            @PathVariable String socialType,
            @RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpServletRequest request
    ) throws Exception {
        System.out.println("\n\nSocial Login - socialType : " + socialType);
        System.out.println("Social Login - code : " + code);
        System.out.println("Social Login - state : " + state);

        Member member = authService.naverSocialLogin(code, state, request);
        System.out.println("\n\nSocial Login - member : " + member);

        Long userNum = authService.isMember(member);
        System.out.println("\n\nSocial Login - isMember : " + userNum);

        if (userNum != null) {
            member.setUserNum(userNum);
        }

        return ResponseEntity.ok(member);
    }

//    @GetMapping("/social/{socialType}")
//    public RedirectView socialLogin(
//            @PathVariable String socialType,
//            @RequestParam("code") String code,
//            @RequestParam("state") String state,
//            HttpServletRequest request
//    ) throws Exception {
//        System.out.println("\n\nSocial Login - socialType : " + socialType);
//        System.out.println("Social Login - code : " + code);
//        System.out.println("Social Login - state : " + state);
//
//        Member member = authService.naverSocialLogin(code, state, request);
//        System.out.println("\n\nSocial Login - member : " + member);
//
//        Long userNum = authService.isMember(member);
//        System.out.println("\n\nSocial Login - isMember : " + userNum);
//
//        AuthResponse authResponse = null;
//        if (userNum != null) { // 존재하는 회원이면
//            member.setUserNum(userNum);
//            authResponse = authService.authenticate(member);
//        }
//
//        request.getSession().setAttribute("accessToken", authResponse.accessToken());
//        request.getSession().setAttribute("refreshToken", authResponse.refreshToken());
//        request.getSession().setAttribute("userId", member.getUserId());
//
//        RedirectView rv = new RedirectView();
//
//        rv.setAttributesMap(Map.of("member", member));
//        rv.setUrl("/"); // 응답할 페이지의 이름
//        return rv;
//    }

    @GetMapping("/social/{socialType}/requrl")
    public ResponseEntity<String> getSocialLoginReqUrl(
            @PathVariable String socialType,
            HttpServletRequest request
    ) throws Exception {
        System.out.println("\n\nSocial Login ReqUrl - socialType : " + socialType);
        return ResponseEntity.ok(authService.getSocialLoginReqUrl(socialType, request));
    }

    @GetMapping("/social/{socialType}/requrl2")
    public ResponseEntity<Member> getSocialLoginReqUrl2(
            @PathVariable String socialType,
            HttpServletRequest request
    ) throws Exception {
        System.out.println("\n\nSocial Login ReqUrl - socialType : " + socialType);
        Map<String, String> resultMap = authService.getSocialLoginReqUrl2(socialType, request);

        String code = resultMap.get("code");
        String state = resultMap.get("state");

        System.out.println("Social Login - code : " + code);
        System.out.println("Social Login - state : " + state);

        Member member = authService.naverSocialLogin(code, state, request);
        System.out.println("\n\nSocial Login - member : " + member);

        Long userNum = authService.isMember(member);
        System.out.println("\n\nSocial Login - isMember : " + userNum);

        if (userNum != null) {
            member.setUserNum(userNum);
        }

        return ResponseEntity.ok(member);
    }

}