package org.ict.allaboutu.admin.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.allaboutu.admin.domain.Report;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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

    private String account;

    private Long boardNum;

    private String userPhone;

    private LocalDateTime enrolleDate;

    private Long reportNum;

    private String reportCause;

    private String reportReason;

    private LocalDateTime deleteDate;

    private List<Report> reports;

    private String enrollType;

}
