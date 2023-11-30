package org.ict.allaboutu.notice.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeDto {

    private long noticeNum;
    private long userNum;
    private String userName;
    private String noticeTitle;
    private String noticeContents;
    private String cartegory;
    private Date eventStart;
    private Date eventEnd;
    private String importance;
    private String importanceDate;
    private Date writeDate;
    private Date modifyDate;
    private String OriginalFileName;
    private String RenameFileName;
    private long ReadCount;

}
