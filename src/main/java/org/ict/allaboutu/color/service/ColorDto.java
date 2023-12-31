package org.ict.allaboutu.color.service;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ColorDto {
    private Long colorNum;

    private String colorImg;

    private String classify;

}
