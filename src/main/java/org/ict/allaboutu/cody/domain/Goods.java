package org.ict.allaboutu.cody.domain;

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
@Table(name = "GOODS")
public class Goods {
    @Id
    @Column(name = "GOODS_NUM")
    private long goodsNum;

    @Column(name = "CODY_NUM")
    private long codyNum;

    @Column(name = "GOODS_NAME")
    private String goodsName;

    @Column(name = "GOODS_IMG")
    private String goodsImg;

    @Column(name = "GOODS_REIMG")
    private String goodsReImg;

    @Column(name = "GOODS_PRICE")
    private String goodsPrice;

    @Column(name = "GOODS_SIZE")
    private String goodsSize;

    @Column(name = "GOODS_LINK")
    private String goodsLink;

    @Column(name = "BRAND_NAME")
    private String brandName;
}
