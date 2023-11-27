package org.ict.allaboutu.board.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "comment")
@SequenceGenerator(name="comment_seq"
        ,sequenceName = "seq_comment_num"
        , initialValue = 1
        , allocationSize = 1)
public class Comment {

    @Id
    @Column(name = "comment_num")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    private int commentNum;
    @Column(name = "board_num")
    private int boardNum;
    @Column(name = "user_num")
    private int userNum;
    @Column(name = "parent_num")
    private int parentNum;
    @Column(name = "content")
    private String content;
    @Column(name = "create_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "board_num")
    private Board board;

}
