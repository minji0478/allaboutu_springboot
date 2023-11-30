package org.ict.allaboutu.admin.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDto implements Serializable {
    private long userNum;

    private String userId;

    private String userName;

    private String userPwd;

    private String userEmail;

    private String admin;

    private String account;

    private long boardNum;

    private String userPhone;

    private LocalDateTime enrolleDate;
}
