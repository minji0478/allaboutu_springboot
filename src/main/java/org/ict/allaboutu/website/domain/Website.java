package org.ict.allaboutu.website.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "WEBSITE")
public class Website {
    @Id
    @Column(name = "WEB_NUM")
    private Long webNum;

    @Column(name = "COLOR_NUM")
    private Long colorNum;

    @Column(name = "WEB_IMG")
    private String webImg;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "WEB_TITLE")
    private String webTitle;

    @Column(name = "PRICE")
    private String price;

}
