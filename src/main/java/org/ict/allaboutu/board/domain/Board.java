package org.ict.allaboutu.board.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "board")
@SequenceGenerator(name="board_seq"
        ,sequenceName = "seq_board_num"
        , initialValue = 1
        , allocationSize = 1)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq")
    @Column(name = "board_num")
    private int boardNum;
    @Column(name = "user_num")
    private int userNum;
    @Column(name = "category_num")
    private int categoryNum;
    @Column(name = "board_title")
    private String boardTitle;
    @Column(name = "board_content")
    private String boardContent;
    @Column(name = "create_date")
    @JsonFormat(pattern="yy. MM. dd")
    private Date createDate;
    @Column(name = "modify_date")
    @JsonFormat(pattern="yy. MM. dd")
    private Date modifyDate;
    @Column(name = "delete_date")
    @JsonFormat(pattern="yy. MM. dd")
    private Date deleteDate;
    @Column(name = "read_count")
    private int readCount;
    private int likeCount;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "board_hashtag_link",
            joinColumns = @JoinColumn(name = "board_num"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_num")
    )
    private List<BoardHashtag> boardHashtags;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Attachment> attachments;

}
