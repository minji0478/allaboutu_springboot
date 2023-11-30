package org.ict.allaboutu.admin.domain;

import jakarta.persistence.*;
import lombok.*;
import org.ict.allaboutu.board.domain.Board;

import java.time.LocalDateTime;
import java.util.List;

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
    private long userNum;

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
    private String userBirth;

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

    @OneToMany
    @JoinColumn(name = "BOARD_NUM")
    private List<Board> board;

}
