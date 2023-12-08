package org.ict.allaboutu.myPage.service;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MyPageDto {
    private Long userNum;

    private String userId;

    private String userName;

    private String userPwd;

    private String userEmail;

    private String userPhone;
}
