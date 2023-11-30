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
@Table(name = "board_comment")
public class Comment {

    @Id
    @Column(name = "comment_num")
    private Long commentNum;
    @Column(name = "board_num")
    private Long boardNum;
    @Column(name = "user_num")
    private Long user_num;
    @Column(name = "parent_num")
    private Long parent_num;
    @Column(name = "content")
    private String content;
    @Column(name = "create_date")
    private LocalDateTime createDate;

}
