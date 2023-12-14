package org.ict.allaboutu.oauth.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.ict.allaboutu.config.repository.TokenRepository;
import org.ict.allaboutu.config.service.JwtService;
import org.ict.allaboutu.config.testModel.TokenType;
import org.ict.allaboutu.config.testModel.Tokens;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.ict.allaboutu.oauth.vo.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse authenticate(Member member) {
        // 사용자의 암호를 검증하기 전에 암호를 인코딩합니다.
//        String encodedPassword = passwordEncoder.encode(member.getUserPwd());
        Member savedMember = memberRepository.findByUserId(member.getUserId());
        String encodedPassword = savedMember.getUserPwd();

        System.out.println("\n\nlogin 테스트 2 : " + member.getUserId());
        System.out.println("\n\nlogin 테스트 2 : " + encodedPassword);

        if ("Y".equals(savedMember.getAccount())) {
            throw new AccessDeniedException("Account is locked");
        }
        // matches 메서드를 사용하여 비밀번호를 확인합니다.
        else if (passwordEncoder.matches(member.getUserPwd(), encodedPassword)) {
            // 인증이 성공하면 나머지 코드를 진행합니다.
            // member.setAdmin(savedMember.getAdmin());
            // member.setUserNum(savedMember.getUserNum());
            String jwtToken = jwtService.generateToken(member);
            String refreshToken = jwtService.generateRefreshToken(member);
            revokeAllUserTokens(member);
            saveToken(member, jwtToken);
            return new AuthResponse(jwtToken, refreshToken);
        } else {
            // 비밀번호가 일치하지 않을 경우 예외 처리 또는 다른 작업 수행
            throw new BadCredentialsException("Invalid password");
        }
    }

    private void revokeAllUserTokens(Member member) {
        List<Tokens> validTokens = tokenRepository.findAllValidTokenByUserId(member.getUserName());
        if (!validTokens.isEmpty()) {
            validTokens.forEach( t-> {
                t.setExpired(true);
                t.setRevoked(true);
            });
            tokenRepository.saveAll(validTokens);
        }
    }
    private void saveToken (Member member, String jwtToken) {
        Tokens token = Tokens.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .userId(member.getUserId())
                .build();
        tokenRepository.save(token);
    }

//    private UserDetails loadUserByUsername(String userId) {
//        // userId을 사용하여 데이터베이스에서 사용자 정보를 가져오는 코드
//        // 이 예시에서는 MemberRepository를 사용한다고 가정합니다.
//        Member member = memberRepository.findByUserId(userId);
//
//        // Spring Security에서 사용하는 UserDetails 객체로 변환
//        return new User(member.getUserId(), member.getUserPwd(), new ArrayList<>());
//    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userId;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userId = jwtService.extractUsername(refreshToken);
        if (userId != null) {
            Member member = this.memberRepository.findByUserId(userId);

            if (member != null && jwtService.isTokenValid(refreshToken, member)) {
                String accessToken = jwtService.generateToken(member);
                AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
