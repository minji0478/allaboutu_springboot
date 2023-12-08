package org.ict.allaboutu.personalcolor.domain;

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
@Table(name = "PERSONAL_COLOR")
public class PersonalColor {
    @Id
    @Column(name = "PERSONAL_NUM")
    private Long personalNum;

    @Column(name = "PERSONAL_NAME")
    private String personalName;

    @Column(name = "PERSONAL_COLOR")
    private String personalColor;

    @Column(name = "PERSONAL_TEXT")
    private String personalText;
}
