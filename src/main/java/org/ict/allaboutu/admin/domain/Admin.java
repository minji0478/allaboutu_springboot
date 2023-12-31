package org.ict.allaboutu.admin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.ict.allaboutu.member.domain.UserRole;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_member")
public class Admin {

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

    @Column(name = "USER_BIRTH")
    private LocalDateTime userBirth;

    @Column(name = "USER_PHONE")
    private String userPhone;

    @Column(name = "USER_PROFILE")
    private String userProfile;

    @Column(name = "ENROLL_TYPE")
    private String enrollType;

    @Column(name = "ENROLL_DATE")
    private LocalDateTime enrollDate;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "ROLE")
    private UserRole role;

//    @OneToMany
//    @JoinColumn(name = "BOARD_NUM")  Dto 에서 사용하는 방법임!!
//    private List<Board> board;

}
