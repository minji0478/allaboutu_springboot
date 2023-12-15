package org.ict.allaboutu.cody.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsDto {
    private String brandName;
    private Long codyNum;
    private String goodsImg;
    private String goodsReImg;
    private String goodsLink;
    private String goodsName;
    private Long goodsNum;
    private String goodsPrice;
    private String goodsSize;
}
