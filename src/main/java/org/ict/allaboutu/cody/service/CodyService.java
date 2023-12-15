package org.ict.allaboutu.cody.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.cody.domain.Cody;
import org.ict.allaboutu.cody.domain.CodyImg;
import org.ict.allaboutu.cody.domain.Goods;
import org.ict.allaboutu.cody.repository.CodyImgRepository;
import org.ict.allaboutu.cody.repository.CodyRepository;
import org.ict.allaboutu.cody.repository.GoodsRepository;
import org.ict.allaboutu.common.FileNameChange;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public Page<CodyDto> getCodyList(Pageable pageable, long formNum) {
        System.out.println("==pageable : " + pageable);
        Page<Cody> codyEntities = codyRepository.findAllByFormNum(pageable, formNum);
        int codyCount = codyRepository.countByStyleNum(formNum);
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
                    .codyCount(codyCount)
                    .build();
        });
        System.out.println("codyDtoPage : " + codyDtoPage);
        return codyDtoPage;
    }

    public CodyDto createCodyImg(CodyDto codyDto, List<MultipartFile> files) throws Exception {
//        // cody 테이블에 저장
//        Long maxCodyNum = codyRepository.findMaxCodyNum();
//        Cody cody = Cody.builder()
//                .codyNum(maxCodyNum == null ? 1 : maxCodyNum + 1)
//                .codyTitle(codyDto.getCodyTitle())
//                .codyContent(codyDto.getCodyContent())
//                .formNum(codyDto.getFormNum())
//                .modelName(codyDto.getModelName())
//                .modelReImg(codyDto.getModelReImg())
//                .modelHeight(codyDto.getModelHeight())
//                .modelWeight(codyDto.getModelWeight())
//                .modelImg(codyDto.getModelImg())
//                .build();
//        Cody saveCody = codyRepository.save(cody);


        // cody_img 테이블에 저장
        List<CodyImg> codyImgs = new ArrayList<>();
        Long maxCodyNum = codyRepository.findMaxCodyNum();
        if (files != null) {
            Long maxCodyImgNum = codyRepository.findMaxCodyImgNum();
            for (MultipartFile file : files) {
                try {
                    String renameFileName = uploadImage(file);

                    CodyImg codyImg = CodyImg.builder()
                            .codyImgNum(maxCodyImgNum == null ? 1 : maxCodyImgNum + 1)
                            .codyImg(file.getOriginalFilename())
                            .codyReImg(renameFileName)
                            .build();

                    codyImgRepository.save(codyImg);

                    codyImgs.add(codyImg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        codyDto = CodyDto.builder()
                .codyNum(maxCodyNum == null ? 1 : maxCodyNum + 1)
                .codyImgs(codyImgs)
                .build();
        return codyDto;
    }


    public CodyDto createCody(CodyDto codyDto, MultipartFile file) {
        Cody cody = new Cody();

        Long maxCodyNum = codyRepository.findMaxCodyNum();
        Long codyNum = (maxCodyNum == null) ? 1L : maxCodyNum + 1L;
        Long maxFornNum = codyRepository.findMaxFormNum();
        Long formNum = (maxFornNum == null) ? 1L : maxFornNum + 1L;

        cody.setCodyNum(codyNum);
        cody.setFormNum(formNum);

        if(file != null){
            String renameFileName = null;
            try {
                renameFileName = FileNameChange.change(file.getOriginalFilename(), "yyyyMMddHHmmss");
                String savePath = System.getProperty("user.dir") + "/src/main/resources/cody_upload/";
                File saveFile = new File(savePath, renameFileName);
                file.transferTo(saveFile);
                cody.setModelImg(file.getOriginalFilename());
                cody.setModelReImg(renameFileName);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        Cody saveCody = codyRepository.save(cody);

        CodyDto resultDto = CodyDto.builder()
                .codyNum(cody.getCodyNum())
                .codyContent(cody.getCodyContent())
                .codyTitle(cody.getCodyTitle())
                .formNum(cody.getFormNum())
                .modelHeight(cody.getModelHeight())
                .modelWeight(cody.getModelWeight())
                .modelName(cody.getModelName())
                .modelImg(cody.getModelImg())
                .modelReImg(cody.getModelReImg())
                .build();

        return resultDto;
    }

    public String uploadImage(MultipartFile file) throws Exception {
        String originalFileName = file.getOriginalFilename();
        String renameFileName = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis()));
        renameFileName += originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

        String savePath = System.getProperty("user.dir") + "/src/main/resources/cody_upload/";
        File saveFile = new File(savePath, renameFileName);
        file.transferTo(saveFile);

        return renameFileName;
    }


    public void deleteCody(Long codyNum) {
        Cody cody = codyRepository.findById(codyNum).get();
        codyRepository.save(cody);
    }

    public GoodsDto createGoods(CodyDto codyDto, MultipartFile file) {
        Goods goods = new Goods();

        Cody cody = codyRepository.findByCodyNum(codyDto.getCodyNum());

        Long maxNum = goodsRepository.findMaxGoodsNumber();
        Long goodsNum = (maxNum== null) ? 1L : maxNum + 1L ;

        goods.setGoodsNum(goodsNum);
        goods.setBrandName(goods.getBrandName());
        goods.setGoodsLink(goods.getGoodsLink());
        goods.setGoodsName(goods.getGoodsName());
        goods.setGoodsPrice(goods.getGoodsPrice());
        goods.setCodyNum(goods.getCodyNum());
        goods.setGoodsSize(goods.getGoodsSize());

        if (file != null) {
            String renameFileName = null;
            try {
                renameFileName = FileNameChange.change(file.getOriginalFilename(), "yyyyMMddHHmmss");
                // 상품 이미지 저장하는거 일단 cody 쪽에 경로 잡았습니다.
                String savePath = System.getProperty("user.dir") + "/src/main/resources/cody_upload/";
                File saveFile = new File(savePath, renameFileName);
                file.transferTo(saveFile);
                goods.setGoodsImg(file.getOriginalFilename());
                goods.setGoodsReImg(renameFileName);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Goods saveGoods = goodsRepository.save(goods);

        GoodsDto resultDto = GoodsDto.builder()
                .goodsNum(goods.getGoodsNum())
                .brandName(goods.getBrandName())
                .goodsLink(goods.getGoodsLink())
                .goodsName(goods.getGoodsName())
                .goodsPrice(goods.getGoodsPrice())
                .codyNum(cody.getCodyNum())
                .goodsSize(goods.getGoodsSize())
                .goodsImg(goods.getGoodsImg())
                .goodsReImg(goods.getGoodsReImg())
                .build();

        return resultDto;
    }

    public void deleteGoods(Long goodsNum) {

        Goods goods = goodsRepository.findById(goodsNum).orElse(null);
        if (goods != null) {
            String savePath = System.getProperty("user.dir") + "/src/main/resources/cody_upload/";
            File deleteFile = new File(savePath + goods.getGoodsReImg());
            deleteFile.delete();
            goodsRepository.delete(goods);
        }
    }
}
