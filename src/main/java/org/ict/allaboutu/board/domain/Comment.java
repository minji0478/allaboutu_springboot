package org.ict.allaboutu.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "board_comment")
public class Comment {

    @Id
    @Column(name = "comment_num")
    private Long commentNum;
    @Column(name = "board_num")
    private Long boardNum;
    @Column(name = "user_num")
    private Long userNum;
    @Column(name = "parent_num")
    private Long parentNum;
    @Column(name = "content")
    private String content;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

}
