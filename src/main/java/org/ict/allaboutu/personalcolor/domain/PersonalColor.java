package org.ict.allaboutu.personalcolor.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
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
