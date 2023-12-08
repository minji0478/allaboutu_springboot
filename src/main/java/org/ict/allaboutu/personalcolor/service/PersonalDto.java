package org.ict.allaboutu.personalcolor.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalDto {
    private Long personalNum;
    private Long userNum;
    private String personalName;
    private String personalColor;
    private String personalText;
    private Long personalUserNum;
    private LocalDateTime personalDate;
    private String personalImg;
    private String personalReimg;

}
