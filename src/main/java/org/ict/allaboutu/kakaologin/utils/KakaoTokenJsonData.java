/*
package org.ict.allaboutu.kakaologin.utils;

import org.ict.allaboutu.kakaologin.utils.dto.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoTokenJsonData {
    private final WebClient webClient;
    private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String REDIRECT_URI = "https://localhost:2222/oauth";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String CLIENT_ID = "b2f72c2fd16ce9a5b5ee4b0c3c6b28e2";

    public KakaoTokenResponse getToken(String code) {
        String uri = TOKEN_URI + "?grant_type=" + GRANT_TYPE + "&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&code=" + code;
        System.out.println(uri);

        KakaoTokenResponse response = webClient.post()
                .uri(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class)
                .block();

        return response;
    }
}
*/
