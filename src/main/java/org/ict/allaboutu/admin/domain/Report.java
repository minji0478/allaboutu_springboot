package org.ict.allaboutu.admin.domain;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REPORT")
public class Report {

    @Id
    @Column(name = "REPORT_NUM")
    private Long reportNum;

    @Column(name = "USER_NUM")
    private Long userNum;

    @Column(name = "BOARD_NUM")
    private Long boardNum;

    @Column(name = "REPORT_USER_NUM")
    private Long reportUserNum;

    @Column(name = "REPORT_CAUSE")
    private String reportCause;

    @Column(name = "REPORT_REASON")
    private String reportReason;

    @Column(name = "DELETE_DATE")
    private LocalDateTime deleteDate;

}