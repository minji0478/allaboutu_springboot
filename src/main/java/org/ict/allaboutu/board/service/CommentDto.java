package org.ict.allaboutu.board.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.service.MemberDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long commentNum;
    private Long boardNum;
    private Long userNum;
    private MemberDto writer;
    private Long parentNum;
    private String content;
    private String createDate;
    private String modifyDate;
}
