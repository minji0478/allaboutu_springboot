package org.ict.allaboutu.member.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.allaboutu.member.domain.ProfileHashtag;
import org.ict.allaboutu.member.domain.UserIdValidation;
import org.ict.allaboutu.member.domain.UserRole;

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
    private UserRole role;
    private List<ProfileHashtag> hashtags;
}
