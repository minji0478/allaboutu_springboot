package org.ict.allaboutu.cody.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "cody_img")
public class CodyImg {
    @Id
    @Column(name = "CODY_IMG_NUM")
    private long codyImgNum;

    @Column(name = "cody_num")
    private long codyNum;

    @Column(name = "cody_img")
    private String codyImg;

    @Column(name = "cody_reimg")
    private String codyReImg;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;
}
