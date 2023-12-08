package org.ict.allaboutu.website.service;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class WebsiteDto {

    private Long webNum;

    public String webImg;

    public String brand;

    public String webTitle;

    public Long price;
}
