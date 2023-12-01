package org.ict.allaboutu.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "board_hashtag_link")
public class BoardHashtagLink {

    @EmbeddedId
    private BoardHashtagLinkPK id;

}
