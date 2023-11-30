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
@Table(name = "board_files")
public class Attachment {

    @Id
    @Column(name = "board_num")
    private long boardNum;
    @Column(name = "attach_num")
    private long attachNum;
    @Column(name = "original_file_name")
    private String originalFileName;
    @Column(name = "rename_file_name")
    private String renameFileName;

}
