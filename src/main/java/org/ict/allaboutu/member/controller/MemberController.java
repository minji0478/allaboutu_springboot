package org.ict.allaboutu.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.ict.allaboutu.member.service.MailDto;
import org.ict.allaboutu.member.service.MailService;
import org.ict.allaboutu.member.service.MemberDto;
import org.ict.allaboutu.member.service.MemberService;

import org.apache.coyote.Response;
import org.ict.allaboutu.config.repository.TokenRepository;
import org.ict.allaboutu.config.service.JwtService;
import org.ict.allaboutu.config.testModel.TokenType;
import org.ict.allaboutu.config.testModel.Tokens;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.ict.allaboutu.member.service.*;
import org.ict.allaboutu.oauth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.io.*;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtService JwtService;

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
        if (!memberService.isValidPassword(rawPassword)) {
            // 유효성 검사에 실패한 경우 에러 응답 반환 또는 예외 처리
            System.out.println("비밀번호 유효성 검사 실패 : " + rawPassword);
            return ResponseEntity.badRequest().build();
        }

        String encPassword = passwordEncoder.encode(rawPassword);
        member.setUserPwd(encPassword);

        return ResponseEntity.ok(memberService.signup(member));
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

    @PostMapping("/login/face")
    public ResponseEntity<Map<String,String>> loginFace(@RequestBody Map<String, String> base64Image, HttpServletRequest request) {
        log.info("페이스로그인");
        String base64ImageStr = base64Image.get("image");
        String userId = base64Image.get("id");
        log.info("userId : " + userId);
        String photoId = "";
        Map<String,String> result = new HashMap<>();
        log.info("userId : " + userId);
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageStr);
        String filePath = "E:/poketAi_workspace/allaboutu_springboot/src/main/resources/faceLoginInput/faceLoginInput.jpg";
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("result", "fail");
            return ResponseEntity.ok().body(result);
        }
        try {
            ProcessBuilder processBuilder = new ProcessBuilder( "E:/poketAi_workspace/allaboutu_springboot/src/main/resources/faceModel/faceLoginCheck.exe","E:/poketAi_workspace/allaboutu_springboot/src/main/resources/faceLoginInput/faceLoginInput.jpg");
            processBuilder.directory(new File("E:/poketAi_workspace/allaboutu_springboot/src/main/resources/faceModel/"));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                photoId += line; //photoId -> 안면인식 로그인 사진 파일명
            }
            int exitCode = process.waitFor();
            log.info("전송완");
            log.info("userId : " + userId);
            log.info("photoId : " + photoId);
            if (userId.equals(photoId)) {
                log.info("userId 랑 photoId 비교 성공");
                Member member = memberRepository.findByUserId(userId);
                log.info("로그인 성공");
                String Token = JwtService.generateToken(member);
                String RefreshToken = JwtService.generateRefreshToken(member);
                result.put("accessToken", Token);
                result.put("refreshToken", RefreshToken);
                result.put("role", member.getRole().toString());
                result.put("result", "success");
                saveToken(member, Token);
                process.destroy();
//                TokenRedis tokenRedis = new TokenRedis(member.get().getMemEmail(), Token, RefreshToken, expiration, refreshTokenExpiration);
//                tokenRedisRepository.save(tokenRedis);
//                Optional<TokenRedis> token = tokenRedisRepository.findById(tokenRedis.getId());
            } else {
                log.info("로그인 실패");
                result.put("result", "failed");
                return ResponseEntity.ok(result);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
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


//    @PostMapping("/login/kakao")
//    public ResponseEntity<Member> kakaoLogin(@RequestBody KakaoLoginRequest request) throws Exception {
//        System.out.println("\n\nKakao Login - accessToken : " + request.getAccessToken());
//        System.out.println("Kakao Login - refreshToken : " + request.getRefreshToken());
//        System.out.println("Kakao Login - userId : " + request.getUserId());
//
//        // 여기서 액세스 토큰을 전달하도록 수정
//        Member member = memberService.kakaoLogin(request);
//        System.out.println("\n\nKakao Login - member : " + member);
//
//        Long userNum = memberService.isMember(member);
//        System.out.println("\n\nKakao Login - isMember : " + userNum);
//
//        if (userNum != null) {
//            member.setUserNum(userNum);
//        }
//
//        return ResponseEntity.ok(member);
//    }
}
