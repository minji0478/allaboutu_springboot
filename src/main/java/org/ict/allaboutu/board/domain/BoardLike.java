package org.ict.allaboutu.board.domain;

import jakarta.persistence.*;
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
    @EmbeddedId
    private BoardLikePK id;
    @Column(name = "create_date")
    private LocalDateTime createDate;
}
