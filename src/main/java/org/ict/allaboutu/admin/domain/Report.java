package org.ict.allaboutu.admin.domain;

import lombok.*;
import jakarta.persistence.*;
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
    private long reportNum;

    @Column(name = "USER_NUM")
    private long userNum;

    @Column(name = "BOARD_NUM")
    private long boardNum;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "REPORT_USER_NUM")
    private long reportUserNum;

    @Column(name = "REPORT_CAUSE")
    private String reportCause;

    @Column(name = "REPORT_REASON")
    private String reportReason;

    @Column(name = "REPORT_COUNT")
    private long reportCount;

}
