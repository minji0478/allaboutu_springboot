package org.ict.allaboutu.board.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.allaboutu.board.domain.Attachment;
import org.ict.allaboutu.board.domain.BoardHashtag;
import org.ict.allaboutu.board.domain.Comment;

import javax.persistence.*;
import java.sql.Date;
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

    private List<Comment> comments;
    private List<BoardHashtag> boardHashtags;
    private List<Attachment> attachments;
}
