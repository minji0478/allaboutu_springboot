package org.ict.allaboutu.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BoardLikePK implements Serializable {
    @Column(name = "board_num")
    private Long boardNum;
    @Column(name = "user_num")
    private Long userNum;
}
