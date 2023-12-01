package org.ict.allaboutu.board.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.allaboutu.board.domain.Attachment;
import org.ict.allaboutu.board.domain.BoardHashtag;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {
    private long boardNum;
    private long userNum;
    private String userId;
    private String userName;
    private long categoryNum;
    private String category;
    private String boardTitle;
    private String boardContent;
    private String createDate;
    private String modifyDate;
    private long readCount;
    private long likeCount;
    private long commentCount;

    private List<CommentDto> comments;
    private List<BoardHashtag> hashtags;
    private List<Attachment> attachments;
}
