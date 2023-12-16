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
@Table(name = "cody")
public class Cody {
    @Id
    @Column(name = "cody_num")
    private long codyNum;

    @Column(name = "form_num")
    private long formNum;

    @Column(name = "cody_title")
    private String codyTitle;

    @Column(name = "cody_content")
    private String codyContent;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "model_img")
    private String modelImg;

    @Column(name = "model_reimg")
    private String modelReImg;

    @Column(name = "model_height")
    private long modelHeight;

    @Column(name = "model_weight")
    private long modelWeight;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

}
