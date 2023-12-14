package org.ict.allaboutu.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "*아이디는 필수 항목입니다.*")
    @Pattern(regexp = "^(?!.*admin).*$", message = "*아이디는 'admin'을 포함할 수 없습니다.*")
    private String userId;

    @Column(name = "USER_NAME")
    @NotBlank(message = "*이름은 필수 항목입니다.*")
//    @Pattern(regexp = "^[가-힣]{2,4}$", message = "*이름은 2글자에서 4글자 사이어야 합니다.*")
    @Size(min = 2, max = 4, message = "*이름은 2글자에서 4글자 사이어야 합니다.*")
//    @Pattern(regexp = "^(?!.*[가-힣]*관리자[가-힣]*$)", message = "*이름에 숫자 또는 '관리자'를 포함할 수 없습니다.*")
    @Pattern(regexp = "^(?!.*관리자).*$", message = "*이름에 '관리자'를 포함할 수 없습니다.*")
    private String userName;

    @Column(name = "USER_PWD")
    @NotBlank(message = "*비밀번호는 필수 항목입니다.*")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])(?=\\S+$).{8,16}$", message = "*비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.*")
//    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~?!@#$%^&*_-]).{5,16}$", message = "*비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.*")
    private String userPwd;

    @Column(name = "USER_EMAIL")
    @NotBlank(message = "*이메일은 필수 항목입니다.*")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "*이메일 형식이 올바르지 않습니다.*")
    private String userEmail;

    @Column(name = "USER_GENDER")
    @NotBlank(message = "*성별은 필수 항목입니다.*")
    private String userGender;

    @Column(name = "USER_PHONE")
    @NotBlank(message = "*전화번호는 필수 항목입니다.*")
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
    @Past(message = "*생년월일은 현재 날짜보다 이전이어야 합니다.*")
    private LocalDateTime userBirth;

    @Column(name = "ROLE")
    private UserRole role;

}
