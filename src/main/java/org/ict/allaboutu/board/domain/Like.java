package org.ict.allaboutu.board.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "board")
@SequenceGenerator(name="board_seq"
        , sequenceName = "seq_board_num"
        , initialValue = 1
        , allocationSize = 1)
public class Like {

    @Id
    @Column(name = "like_num")
    private long likeNum;
    @Column(name = "board_num")
    private long boardNum;
    @Column(name = "user_num")
    private long userNum;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "checked")
    private String checked;

}
