package org.ict.allaboutu.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class AttachmentPK implements Serializable {

    @Column(name = "board_num")
    private Long boardNum;
    @Column(name = "attach_num")
    private Long attachNum;
}
