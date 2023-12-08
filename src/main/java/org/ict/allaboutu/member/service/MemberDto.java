package org.ict.allaboutu.member.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.allaboutu.member.domain.ProfileHashtag;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    private Long userNum;
    private String userId;
    private String userName;
    private String userPwd;
    private String userEmail;
    private String userGender;
    private String userPhone;
    private String userProfile;
    private String enrollType;
    private String enrollDate;
    private String admin;
    private String account;
    private Long reportCount;
    private String userBirth;

    private List<ProfileHashtag> hashtags;
}
