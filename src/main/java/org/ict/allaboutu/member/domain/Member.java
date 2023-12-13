package org.ict.allaboutu.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_member")
public class Member /* implements UserDetails */ {
    @Id
    @Column(name = "USER_NUM")
    private Long userNum;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_PWD")
    private String userPwd;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Column(name = "USER_GENDER")
    private String userGender;

    @Column(name = "USER_PHONE")
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

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
}
