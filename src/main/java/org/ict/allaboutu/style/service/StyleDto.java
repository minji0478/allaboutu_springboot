package org.ict.allaboutu.style.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StyleDto {
    private long styleNum;
    private long userNum;
    private long formNum;
    private String userImg;
    private String userStyle;
    private long height;
    private long weight;
    private long shoulder;
    private long waist;
    private long arm;
    private long leg;
    private Date styleDate;

}
