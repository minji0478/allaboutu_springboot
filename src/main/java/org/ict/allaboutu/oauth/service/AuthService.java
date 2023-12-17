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
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
            System.out.println("\n\nlogin 테스트 3 : " + member.getUserId());
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

    public Long isMember(Member member) {
        Member savedMember = memberRepository.findByUserId(member.getUserId());
        if (savedMember != null) {
            return savedMember.getUserNum();
        } else {
            return null;
        }
    }

    public String getSocialLoginReqUrl(String socialType, HttpServletRequest request) {
        String clientId = "NGMnpWhuYxqpVAAkWCsZ";
        String redirectUri = "http://localhost:2222/login/naver/callback";
//        String redirectUri = "http://localhost:2222/auth/social/naver";
        String state = generateState();

        String reqUrl = "https://nid.naver.com/oauth2.0/authorize" +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&state=" + state;

        request.getSession().setAttribute("state", state);

        return reqUrl;
    }

    public Map<String, String> getSocialLoginReqUrl2(String socialType, HttpServletRequest request) throws Exception {
        String clientId = "NGMnpWhuYxqpVAAkWCsZ";
        String redirectUri = "http://localhost:2222/login/naver/callback";
        String state = generateState();

        String firstRequestUrl = "https://nid.naver.com/oauth2.0/authorize" +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&state=" + state;

        request.getSession().setAttribute("state", state);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(firstRequestUrl, String.class); // 다른 서버로 GET 요청 보내기
        String responseBody = responseEntity.getBody(); // 응답 처리
        System.out.println("\n\nSocial Login - first responseBody : " + responseBody);

        JSONObject responseBodyJson = new JSONObject(responseBody);
        String accessToken = responseBodyJson.getString("code");
        String refreshToken = responseBodyJson.getString("state");

        Map<String, String> resultMap = new ObjectMapper().readValue(responseBody, Map.class);

        return resultMap;
    }

    public String generateState()
    {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public Member naverSocialLogin(String code, String state, HttpServletRequest request) throws Exception {
        String clientId = "NGMnpWhuYxqpVAAkWCsZ";
        String clientSecret = "Xren5NUtVq";
        String tokenRequestUrl = "https://nid.naver.com/oauth2.0/token" +
                "?client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&grant_type=authorization_code" +
                "&state=" + state +
                "&code=" + code;

        String savedState = (String) request.getSession().getAttribute("state");

        if (!state.equals(savedState)) {
            throw new Exception("Invalid state");
        } else {
            request.getSession().removeAttribute("state");
        }

        // 액세스 토큰 발급 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(tokenRequestUrl, String.class); // 다른 서버로 GET 요청 보내기
        String responseBody = responseEntity.getBody(); // 응답 처리

        JSONObject responseBodyJson = new JSONObject(responseBody);
        String accessToken = responseBodyJson.getString("access_token");
        String refreshToken = responseBodyJson.getString("refresh_token");
        String tokenType = responseBodyJson.getString("token_type");
        String expiresIn = responseBodyJson.getString("expires_in");

        System.out.println("\n\nSocial Login - responseBody : " + responseBody);
        System.out.println("Social Login - accessToken : " + accessToken);
        System.out.println("Social Login - refreshToken : " + refreshToken);
        System.out.println("Social Login - tokenType : " + tokenType);
        System.out.println("Social Login - expiresIn : " + expiresIn);


        // 프로필 정보 요청
        URL profileRequestUrl = new URL("https://openapi.naver.com/v1/nid/me");
        HttpURLConnection conn = (HttpURLConnection) profileRequestUrl.openConnection();

        // POST 요청을 위해 기본값이 false인 setDoOutput을 true로
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + accessToken); // 요청 헤더 설정

        // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder profileResponseBuilder = new StringBuilder();
        while ((line = br.readLine()) != null) {
            profileResponseBuilder.append(line);
        }
        br.close();
        System.out.println("\n\nSocial Login - profileResponse : " + profileResponseBuilder.toString());

        // JSON 타입의 문자열을 객체로 변환
        JSONObject profileResponseJson = new JSONObject(profileResponseBuilder.toString());
        JSONObject memberInfoJson = profileResponseJson.getJSONObject("response");
        Member member = Member.builder()
                .userId(memberInfoJson.getString("id"))
                .userPwd(memberInfoJson.getString("id").substring(0, 9) + "@Naver7")
                .userName(memberInfoJson.getString("nickname"))
                .userEmail(memberInfoJson.getString("email"))
                .userGender(memberInfoJson.getString("gender"))
                .userPhone(memberInfoJson.getString("mobile"))
                .enrollType("NAVER")
                .build();

        System.out.println("\n\nSocial Login - member : " + member.toString());

        return member;
    }
}
