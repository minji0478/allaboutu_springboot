package org.ict.allaboutu.board.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {
    private long boardNum;
    private long userNum;
    private String userId;
    private long categoryNum;
    private String category;
    private String boardTitle;
    private String boardContent;
    private String createDate;
    private String modifyDate;
    private long readCount;
    private long likeCount;
}
