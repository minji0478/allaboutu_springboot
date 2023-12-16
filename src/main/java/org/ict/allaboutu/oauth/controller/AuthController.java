package org.ict.allaboutu.oauth.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.apache.tomcat.util.http.parser.Authorization;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.service.MemberDto;
import org.ict.allaboutu.oauth.vo.AuthResponse;
import org.ict.allaboutu.oauth.service.AuthService;
import org.ict.allaboutu.oauth.vo.AuthRequest;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

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
            @RequestParam("state") String state
    ) throws Exception {
        System.out.println("\n\nSocial Login - socialType : " + socialType);
        System.out.println("Social Login - code : " + code);
        System.out.println("Social Login - state : " + state);

        Member member = authService.socialLogin(socialType, code, state);
        System.out.println("\n\nSocial Login - member : " + member);

        Long userNum = authService.isMember(member);
        System.out.println("\n\nSocial Login - isMember : " + userNum);

        if (userNum != null) {
            member.setUserNum(userNum);
        }

        return ResponseEntity.ok(member);
    }

    @GetMapping("/social/{socialType}/requrl")
    public ResponseEntity<String> getSocialLoginReqUrl(
            @PathVariable String socialType,
            HttpServletRequest request
    ) throws Exception {
        System.out.println("\n\nSocial Login ReqUrl - socialType : " + socialType);
        return ResponseEntity.ok(authService.getSocialLoginReqUrl(socialType, request));
    }

}