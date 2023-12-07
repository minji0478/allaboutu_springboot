package org.ict.allaboutu.cody.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Attachment;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.domain.BoardHashtag;
import org.ict.allaboutu.board.domain.Comment;
import org.ict.allaboutu.board.repository.BoardRepository;
import org.ict.allaboutu.board.service.BoardDto;
import org.ict.allaboutu.board.service.CommentDto;
import org.ict.allaboutu.cody.domain.Cody;
import org.ict.allaboutu.cody.domain.CodyImg;
import org.ict.allaboutu.cody.domain.Goods;
import org.ict.allaboutu.cody.repository.CodyImgRepository;
import org.ict.allaboutu.cody.repository.CodyRepository;
import org.ict.allaboutu.cody.repository.GoodsRepository;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.style.service.StyleDto;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@PropertySource("classpath:application.properties")
public class CodyService {

    private final CodyRepository codyRepository;
    private final CodyImgRepository codyImgRepository;
    private final GoodsRepository goodsRepository;

    @Transactional
    public Page<CodyDto> getCodyList(Pageable pageable) {
        System.out.println("==pageable : " + pageable);
        Page<Cody> codyEntities = codyRepository.findAll(pageable);

        System.out.println("==codyEntities : " + codyEntities);

        Page<CodyDto> codyDtoPage = codyEntities.map(codyEntity -> {
            System.out.println("==codyEntity : " + codyEntity);
            // 해시태그 및 첨부파일 목록 조회
            List<CodyImg> codyImgList = codyImgRepository.findAllByCodyNum(codyEntity.getCodyNum());
            List<Goods> goodsList = goodsRepository.findAllByCodyNum(codyEntity.getCodyNum());
            System.out.println("==codyImgList : " + codyImgList);
            System.out.println("==goodsList : " + goodsList);

            return CodyDto.builder()
                    .codyNum(codyEntity.getCodyNum())
                    .formNum(codyEntity.getFormNum())
                    .codyTitle(codyEntity.getCodyTitle())
                    .codyContent(codyEntity.getCodyContent())
                    .modelName(codyEntity.getModelName())
                    .modelImg(codyEntity.getModelImg())
                    .modelHeight(codyEntity.getModelHeight())
                    .modelWeight(codyEntity.getModelWeight())
                    .codyImgList(codyImgList)
                    .goodsList(goodsList)
                    .build();
        });
        System.out.println("codyDtoPage : " + codyDtoPage);
        return codyDtoPage;
    }
}
