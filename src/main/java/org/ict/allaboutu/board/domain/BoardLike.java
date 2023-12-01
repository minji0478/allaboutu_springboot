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
@Table(name = "board_like")
public class BoardLike {

    @Id
    @Column(name = "like_num")
    private Long likeNum;
    @Column(name = "board_num")
    private Long boardNum;
    @Column(name = "user_num")
    private Long userNum;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "checked")
    private String checked;

}
