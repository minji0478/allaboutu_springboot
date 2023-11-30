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
@Table(name = "board")
public class Board {

    @Id
    @Column(name = "board_num")
    private Long boardNum;
    @Column(name = "user_num")
    private Long userNum;
    @Column(name = "category_num")
    private Long categoryNum;
    @Column(name = "board_title")
    private String boardTitle;
    @Column(name = "board_content")
    private String boardContent;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;
    @Column(name = "delete_date")
    private LocalDateTime deleteDate;
    @Column(name = "read_count")
    private Long readCount;

}
