package org.ict.allaboutu.notice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.stereotype.Service;


import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "notice")
public class Notice {
    @Id
    @Column(name="notice_num")
    private long noticeNum;
    @Column(name="user_num")
    private long userNum;
    @Column(name="notice_title")
    private String noticeTitle;
    @Column(name="notice_contents")
    private String noticeContents;
    @Column(name="cartegory")
    private String cartegory;
    @Column(name="eventStart")
    private Date eventStart;
    @Column(name="eventEnd")
    private Date eventEnd;
    @Column(name="importance")
    private String importance;
    @Column(name="importance_date")
    private String importanceDate;
    @Column(name="write_date")
    private Date writeDate;
    @Column(name="modify_date")
    private Date modifyDate;
    @Column(name="OriginalFileName")
    private String OriginalFileName;
    @Column(name="RenameFileName")
    private String RenameFileName;
    @Column(name="ReadCount")
    private long ReadCount;

}
