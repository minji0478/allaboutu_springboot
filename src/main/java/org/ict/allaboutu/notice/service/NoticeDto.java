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

    private Long noticeNum;
    private Long userNum;
    private String userName;
    private String noticeTitle;
    private String noticeContents;
    private String cartegory;
    private String eventStart;
    private String eventEnd;
    private String importance;
    private String importanceDate;
    private String writeDate;
    private String modifyDate;
    private String originalFileName;
    private String renameFileName;
    private Long readCount;

}
