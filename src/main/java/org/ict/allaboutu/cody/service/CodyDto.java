package org.ict.allaboutu.cody.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ict.allaboutu.cody.domain.CodyImg;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodyDto {
    private long codyNum;
    private long formNum;
    private String codyTitle;
    private String codyContent;
    private String modelName;
    private String modelImg;
    private String modelReImg;
    private long modelHeight;
    private long modelWeight;

    private List goodsList;
    private List codyImgList;
    private int codyCount;

    private List<CodyImg> codyImgs;

}
