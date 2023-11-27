package org.ict.allaboutu.board.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "board_files")
public class Attachment {

    @Id
    @Column(name = "board_num")
    private int boardNum;
    @Id
    @Column(name = "attach_num")
    private int attachNum;
    @Column(name = "original_file_name")
    private String originalFileName;
    @Column(name = "rename_file_name")
    private String renameFileName;

    @ManyToOne
    @JoinColumn(name = "board_num")
    private Board board;

}
