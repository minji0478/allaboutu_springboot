package org.ict.allaboutu.member.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoLoginRequest {
    private String accessToken;
    private String refreshToken;
    private String userId;
}
