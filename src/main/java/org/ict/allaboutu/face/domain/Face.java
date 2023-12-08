package org.ict.allaboutu.face.domain;


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
@Builder
@Table(name = "Face")
public class Face {
    @Id
    @Column(name = "FACE_NUM")
    private Long faceNum;

    @Column(name = "USER_NUM")
    private Long userNum;

    @Column(name = "COLOR_NUM")
    private Long colorNum;

    @Column(name = "IMG_BEFORE")
    private String imgBefore;

    @Column(name = "IMG_AFTER")
    private String imgAfter;
}
