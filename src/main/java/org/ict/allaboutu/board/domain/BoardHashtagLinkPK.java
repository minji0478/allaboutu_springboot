package org.ict.allaboutu.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class BoardHashtagLinkPK implements Serializable {

    @Column(name = "board_num")
    private Long boardNum;
    @Column(name = "hashtag_num")
    private Long hashtagNum;

}
