package org.ict.allaboutu.board.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
