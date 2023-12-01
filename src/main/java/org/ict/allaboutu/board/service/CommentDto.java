package org.ict.allaboutu.board.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long commentNum;
    private Long boardNum;
    private Long userNum;
    private String userId;
    private String userName;
    private Long parentNum;
    private String content;
    private String createDate;
    private String modifyDate;
}
