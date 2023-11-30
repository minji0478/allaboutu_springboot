package org.ict.allaboutu.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board_hashtag")
public class BoardHashtag {

    @Id
    @Column(name = "hashtag_num")
    private long hashtagNum;
    @Column(name = "hashtag")
    private String hashtag;

}
