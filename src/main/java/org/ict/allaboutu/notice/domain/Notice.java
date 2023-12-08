package org.ict.allaboutu.notice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Long noticeNum;
    @Column(name="user_num")
    private Long userNum;
    @Column(name="notice_title")
    private String noticeTitle;
    @Column(name="notice_contents")
    private String noticeContents;
    @Column(name="cartegory")
    private String cartegory;
    @Column(name="eventStart")
    private LocalDateTime eventStart;
    @Column(name="eventEnd")
    private LocalDateTime eventEnd;
    @Column(name="importance")
    private String importance;
    @Column(name="importance_date")
    private LocalDateTime importanceDate;
    @Column(name="write_date")
    private LocalDate writeDate;
    @Column(name="modify_date")
    private LocalDateTime modifyDate;
    @Column(name="original_file_name")
    private String originalFileName;
    @Column(name="rename_file_name")
    private String renameFileName;
    @Column(name="read_count")
    private Long readCount;

}
