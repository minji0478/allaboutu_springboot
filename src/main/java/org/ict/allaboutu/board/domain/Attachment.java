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
@Table(name = "board_files")
public class Attachment {

    @EmbeddedId
    private AttachmentPK id;
    @Column(name = "original_file_name")
    private String originalFileName;
    @Column(name = "rename_file_name")
    private String renameFileName;

}
