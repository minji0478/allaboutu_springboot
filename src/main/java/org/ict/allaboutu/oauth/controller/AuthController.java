package org.ict.allaboutu.oauth.controller;

import java.io.IOException;

import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.oauth.vo.AuthResponse;
import org.ict.allaboutu.oauth.service.AuthService;
import org.ict.allaboutu.oauth.vo.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

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

}