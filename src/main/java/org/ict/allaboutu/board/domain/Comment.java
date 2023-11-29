package org.ict.allaboutu.board.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board_comment")
//@SequenceGenerator(name="comment_seq"
//        , sequenceName = "seq_board_num"
//        , initialValue = 1
//        , allocationSize = 1)
public class Comment {

    @Id
    @Column(name = "comment_num")
    private long commentNum;
    @Column(name = "board_num")
    private long boardNum;
    @Column(name = "user_num")
    private long user_num;
    @Column(name = "parent_num")
    private long parent_num;
    @Column(name = "content")
    private String content;
    @Column(name = "create_date")
    private LocalDateTime createDate;

}
