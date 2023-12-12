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
public class ReportDto implements Serializable {
    private long boardNum;

    private LocalDateTime deleteDate;

    private String reportCause;

    private long reportNum;

    private String reportReason;

    private long reportUserNum;

    private long userNum;

    private String reportUserId;
}
