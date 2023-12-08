package org.ict.allaboutu.color.domain;

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
@Table(name = "color")
public class Color {
    @Id
    @Column(name = "COLOR_NUM")
    private Long colorNum;

    @Column(name = "R_NUM")
    private Long rNum;

    @Column(name = "G_NUM")
    private Long gNum;

    @Column(name = "B_NUM")
    private Long bNum;

    @Column(name = "COLOR_IMG")
    private String colorImg;

    @Column(name = "CLASSIFY")
    private String classify;

    @Column(name = "COLOR_NAME")
    private String colorName;
}
