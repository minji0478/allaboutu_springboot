package org.ict.allaboutu.member.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.domain.UserRole;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "allaboutu.mailservice@gmail.com";

    public MemberDto signup(Member member) {

        member.setUserNum(memberRepository.findMaxUserNum() + 1L);
        member.setEnrollDate(LocalDateTime.now());
        member.setAccount("N");
        member.setReportCount(0L);
        if (member.getEnrollType() == null) {
            member.setEnrollType("normal");
        }
        member.setAccount("N");
        member.setRole(UserRole.USER);
        Member savedMember = memberRepository.save(member);




        return MemberDto.builder()
                .userNum(savedMember.getUserNum())
                .userId(savedMember.getUserId())
                .userName(savedMember.getUserName())
                .userPwd(savedMember.getUserPwd())
                .userEmail(savedMember.getUserEmail())
                .userGender(savedMember.getUserGender())
                .userPhone(savedMember.getUserPhone())
                .userProfile(savedMember.getUserProfile())
                .enrollType(savedMember.getEnrollType())
                .enrollDate(savedMember.getEnrollDate().toString())
                .account(savedMember.getAccount())
                .reportCount(savedMember.getReportCount())
                .role(savedMember.getRole())
                .build();
    }

    public MemberDto getMember(String userId) {
        Member member = memberRepository.findByUserId(userId);

        return MemberDto.builder()
                .userNum(member.getUserNum())
                .userId(member.getUserId())
                .userName(member.getUserName())
                .userPwd(member.getUserPwd())
                .userEmail(member.getUserEmail())
                .userGender(member.getUserGender())
                .userPhone(member.getUserPhone())
                .userProfile(member.getUserProfile())
                .enrollType(member.getEnrollType())
                .enrollDate(member.getEnrollDate().toString())
                .account(member.getAccount())
                .reportCount(member.getReportCount())
                .role(member.getRole())
                .build();
    }

    public boolean isValidPassword(String password) {
        // 비밀번호의 유효성 검사 로직을 추가
        // 여기서는 8~16자의 길이, 영문 대소문자, 숫자, 특수문자가 각각 1개 이상 포함되어 있는지 확인합니다.
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$";
        return password != null && password.matches(passwordPattern);
    }

    public String getUserId(Long userNum) {
        return memberRepository.findById(userNum).get().getUserId();
    }

    public String findIdByUserEmail(String userEmail){
        List<Member> memberList = memberRepository.findByUserEmail(userEmail);
        List<String> userIdList = memberList.stream().map(Member::getUserId).toList();
        return memberList != null ? StringUtils.join(userIdList) : null;
    }

    public boolean checkUserInfo(String userId, String userEmail) {
        Member member = memberRepository.findByUserId(userId);
        return member != null && member.getUserEmail().equals(userEmail);
    }

    public MemberDto changePassword(Member member) {
        Member originalMember = memberRepository.findByUserId(member.getUserId());

        originalMember.setUserPwd(member.getUserPwd());
        memberRepository.save(originalMember);

        return MemberDto.builder()
                .userNum(originalMember.getUserNum())
                .userId(originalMember.getUserId())
                .userName(originalMember.getUserName())
                .userPwd(originalMember.getUserPwd())
                .userEmail(originalMember.getUserEmail())
                .userGender(originalMember.getUserGender())
                .userPhone(originalMember.getUserPhone())
                .userProfile(originalMember.getUserProfile())
                .enrollType(originalMember.getEnrollType())
                .enrollDate(originalMember.getEnrollDate().toString())
                .account(originalMember.getAccount())
                .userBirth(originalMember.getUserBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .reportCount(originalMember.getReportCount())
                .role(originalMember.getRole())
                .build();
    }

    public String kakaoLogin(HttpServletRequest request, String code) throws Exception {

        String accessToken = "";

        // restTemplate을 사용하여 API 호출
        RestTemplate restTemplate = new RestTemplate();
        String reqUrl = "/oauth/token";
        URI uri = URI.create("https://kauth.kakao.com/oauth/token");

        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.set("grant_type", "authorization_code");
        parameters.set("client_id", "6582a873bd8826c412d67ce8db075b24");
        parameters.set("redirect_uri", "http://localhost:2222" + "/login/kakao");
        parameters.set("code", code);

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(parameters, headers);
        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        accessToken = (String) responseBody.get("access_token");

        return accessToken;
    }

//    public Member kakaoLogin(String code) throws JSONException {
//        String clientId = "77ce8b02401feae3023839c702816a35";
//        System.out.println("=====1111clientId : " + clientId + "=====");
//        String redirectUri = "http://localhost:2222/login/kakao";
//        System.out.println("=====22222redirectUri : " + redirectUri + "=====");
//
//        String tokenRequestUrl = "https://kauth.kakao.com/oauth/token" +
//                "?grant_type=authorization_code" +
//                "&client_id=" + clientId +
//                "&redirect_uri=" + redirectUri +
//                "&code=" + code;
//        System.out.println("=====33333tokenRequestUrl : " + tokenRequestUrl + "=====");
//
//        // 액세스 토큰 발급 요청
//        RestTemplate restTemplate = new RestTemplate();
//        System.out.println("=====33restTemplate : " + restTemplate + "=====");
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(tokenRequestUrl, restRequest, String.class); // 다른 서버로 POST 요청 보내기
//        System.out.println("=====44responseEntity : " + responseEntity + "=====");
//        String responseBody = responseEntity.getBody(); // 응답 처리
//        System.out.println("=====55responseBody : " + responseBody + "=====");
//
//        JSONObject responseBodyJson = new JSONObject(responseBody);
//        System.out.println("=====44444responseBodyJson : " + responseBodyJson + "=====");
//        String receivedAccessToken = responseBodyJson.getString("access_token");
//        System.out.println("=====55555receivedAccessToken : " + receivedAccessToken + "=====");
//        String receivedRefreshToken = responseBodyJson.getString("refresh_token");
//        System.out.println("=====66666receivedRefreshToken : " + receivedRefreshToken + "=====");
//        String enrollType = responseBodyJson.getString("enroll_type");
//        System.out.println("=====77777enrollType : " + enrollType + "=====");
//        String expiresIn = responseBodyJson.getString("expires_in");
//        System.out.println("=====88888expiresIn : " + expiresIn + "=====");
//
//        System.out.println("\n\nKakao Login - responseBody : " + responseBody);
//        System.out.println("Kakao Login - receivedAccessToken : " + receivedAccessToken);
//        System.out.println("Kakao Login - receivedRefreshToken : " + receivedRefreshToken);
//        System.out.println("Kakao Login - enrollType : " + enrollType);
//        System.out.println("Kakao Login - expiresIn : " + expiresIn);
//
//        // 받아온 토큰 등을 사용하여 Member 객체를 생성하거나 다른 처리를 수행해야 합니다.
//
//        return null;
//    }


//    public Long isMember(Member member) {
//        Member savedMember = memberRepository.findByUserId(member.getUserId());
//        if (savedMember != null) {
//            return savedMember.getUserNum();
//        } else {
//            return null;
//        }
//    }

}
