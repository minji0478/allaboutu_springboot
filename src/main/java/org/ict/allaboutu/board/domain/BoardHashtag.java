package org.ict.allaboutu.board.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "board_hashtag")
@SequenceGenerator(name="board_hashtag_seq"
        ,sequenceName = "seq_board_hashtag_num"
        , initialValue = 1
        , allocationSize = 1)
public class BoardHashtag {

    @Id
    @Column(name = "hashtag_num")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_hashtag_seq")
    private int hashtagNum;
    @Column(name = "hashtag")
    private String hashtag;

    @ManyToMany(mappedBy = "boardHashtags")
    private List<Comment> comments;

}
