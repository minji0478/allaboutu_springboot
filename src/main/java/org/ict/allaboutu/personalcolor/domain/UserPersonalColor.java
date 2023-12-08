package org.ict.allaboutu.personalcolor.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
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
    private LocalDateTime personalDate;

    @Column(name = "PERSONAL_IMG")
    private String personalImg;

    @Column(name = "PERSONAL_REIMG")
    private String personalReimg;
}
