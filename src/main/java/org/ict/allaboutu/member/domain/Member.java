package org.ict.allaboutu.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_member")
public class Member {
    @Id
    @Column(name = "USER_NUM")
    private Long userNum;

    @Column(name = "USER_ID")
    @NotBlank(message = "아이디는 필수 항목입니다.")
    @UserIdValidation(message = "아이디에 'admin'을 포함할 수 없습니다.")
    private String userId;

    @Column(name = "USER_NAME")
    @NotBlank(message = "이름은 필수 항목입니다.")
    @UserNameValidation(message = "이름은 6글자 이상으로 만들 수 없습니다.")
    private String userName;

    @Column(name = "USER_PWD")
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @UserPwdValidation(message = "비밀번호에는 특수문자가 포함되어야 합니다.")
    private String userPwd;

    @Column(name = "USER_EMAIL")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String userEmail;

    @Column(name = "USER_GENDER")
    @NotBlank(message = "성별은 필수 항목입니다.")
    private String userGender;

    @Column(name = "USER_PHONE")
    @NotBlank(message = "전화번호는 필수 항목입니다.")
    private String userPhone;

    @Column(name = "USER_PROFILE")
    private String userProfile;

    @Column(name = "ENROLL_TYPE")
    private String enrollType;

    @Column(name = "ENROLL_DATE")
    private LocalDateTime enrollDate;

    @Column(name = "ADMIN")
    private String admin;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "REPORT_COUNT")
    private Long reportCount;

    @Column(name = "USER_BIRTH")
    private LocalDateTime userBirth;

    @Column(name = "ROLE")
    private UserRole role;

}
