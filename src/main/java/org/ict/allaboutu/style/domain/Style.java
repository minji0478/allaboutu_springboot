package org.ict.allaboutu.style.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "style")
public class Style {
    @Id
    @Column(name = "style_num")
    private long styleNum;

    @Column(name = "user_num")
    private long userNum;

    @Column(name = "form_num")
    private long formNum;

    @Column(name = "user_img")
    private String userImg;

    @Column(name = "user_reimg")
    private String userReimg;

    @Column(name = "user_style")
    private String userStyle;

    @Column(name = "user_restyle")
    private String userRestyle;

    @Column(name = "height")
    private long height;

    @Column(name = "weight")
    private long weight;

    @Column(name = "shoulder")
    private long shoulder;

    @Column(name = "waist")
    private long waist;

    @Column(name = "arm")
    private long arm;

    @Column(name = "leg")
    private long leg;

    @Column(name = "style_date")
    private Date styleDate;

}
