package org.ict.allaboutu.board.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board")
//@SequenceGenerator(name="board_seq"
//        , sequenceName = "seq_board_num"
//        , initialValue = 1
//        , allocationSize = 1)
public class Board {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq")
    @Column(name = "board_num")
    private long boardNum;
    @Column(name = "user_num")
    private long userNum;
    @Column(name = "category_num")
    private long categoryNum;
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
    private long readCount;

//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
//    private List<Comment> comments;
//
//    @ManyToMany
//    @JoinTable(
//            name = "board_hashtag_link",
//            joinColumns = @JoinColumn(name = "board_num"),
//            inverseJoinColumns = @JoinColumn(name = "hashtag_num")
//    )
//    private List<BoardHashtag> boardHashtags;
//
//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
//    private List<Attachment> attachments;

}
