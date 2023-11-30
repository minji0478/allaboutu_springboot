package org.ict.allaboutu.personalcolor.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "USER_PERSONAL_COLOR")
public class UserPersonalColor {
    @Id
    @Column(name = "PERSONAL_USER_NUM")
    private Long personalUserNum;

    @Column(name = "USER_NUM")
    private Long userNum;

    @Column(name = "PERSONAL_NUM")
    private Long personalNum;

    @Column(name = "PERSONAL_DATE")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date personalDate;

    @Column(name = "PERSONAL_IMG")
    private String personalImg;

}
