package org.ict.allaboutu.board.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.allaboutu.board.domain.Attachment;
import org.ict.allaboutu.board.domain.BoardHashtag;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.service.MemberDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {

    private Long boardNum;
    private Long userNum;
    private MemberDto writer;
    private Long categoryNum;
    private String category;
    private String boardTitle;
    private String boardContent;
    private String createDate;
    private String modifyDate;
    private Long readCount;
    private Long likeCount;
    private Long commentCount;
    private Long rank;

    private List<CommentDto> comments;
    private List<BoardHashtag> hashtags;
    private List<Attachment> attachments;

}
