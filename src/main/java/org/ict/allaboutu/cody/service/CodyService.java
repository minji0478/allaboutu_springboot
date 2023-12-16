package org.ict.allaboutu.cody.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
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
import java.time.LocalDateTime;
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
                    .modelReImg(codyEntity.getModelReImg())
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

    @Transactional
    public Page<CodyDto> getCodyListAll(Pageable pageable){
        Page<Cody> codyEntities = codyRepository.findAll(pageable);
        int codyCount = (int) codyRepository.count();

        System.out.println("== all ==codyCount : " + codyCount);
        Page<CodyDto> codyDtoPage = codyEntities.map(codyEntity -> {
            System.out.println("== all ==codyEntity : " + codyEntity);
            List<CodyImg> codyImgList = codyImgRepository.findAllByCodyNum(codyEntity.getCodyNum());
            List<Goods> goodsList = goodsRepository.findAllByCodyNum(codyEntity.getCodyNum());
            System.out.println("== all ==codyImgList : " + codyImgList);
            System.out.println("== all ==goodsList : " + goodsList);

            return CodyDto.builder()
                    .codyNum(codyEntity.getCodyNum())
                    .formNum(codyEntity.getFormNum())
                    .codyTitle(codyEntity.getCodyTitle())
                    .codyContent(codyEntity.getCodyContent())
                    .modelName(codyEntity.getModelName())
                    .modelImg(codyEntity.getModelImg())
                    .modelReImg(codyEntity.getModelReImg())
                    .modelHeight(codyEntity.getModelHeight())
                    .modelWeight(codyEntity.getModelWeight())
                    .codyImgList(codyImgList)
                    .goodsList(goodsList)
                    .codyCount(codyCount)
                    .build();
        });
        System.out.println("== all == codyDtoPage : " + codyDtoPage);
        return codyDtoPage;
    }


    public CodyDto createCody(
            CodyDto codyDto, List<MultipartFile> attachments, MultipartFile modelImg,
            List<GoodsDto> goodsDto,List<MultipartFile> goodsAttachment) throws Exception {
        // cody 테이블에 저장
        Long maxCodyNum = codyRepository.findMaxCodyNum();
        Long myCodyNum = maxCodyNum == null ? 1 : maxCodyNum + 1;
        int saveCount = 1;

        //CodyDto myCody = createCody(codyDto, modelImg);
        //모델이미지
        if(modelImg != null){
            try {
                String renameFileName = uploadImage(modelImg, saveCount);
                saveCount ++;
                codyDto.setModelImg(modelImg.getOriginalFilename());
                codyDto.setModelReImg(renameFileName);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        //코디
        Cody cody = Cody.builder()
                .codyNum(myCodyNum)
                .codyTitle(codyDto.getCodyTitle())
                .codyContent(codyDto.getCodyContent())
                .formNum(codyDto.getFormNum())
                .modelName(codyDto.getModelName())
                .modelReImg(codyDto.getModelReImg())
                .modelHeight(codyDto.getModelHeight())
                .modelWeight(codyDto.getModelWeight())
                .modelImg(codyDto.getModelImg())
                .build();
        Cody saveCody = codyRepository.save(cody);
        System.out.println("=====saveCody : " + saveCody);
        // cody_img 테이블에 저장
        List<CodyImg> codyImgs = new ArrayList<>();

        //코디 이미지
        if (attachments != null) {
            String renameFileName = null;
            for (MultipartFile file : attachments) {
                try {
                    Long maxCodyImgNum = codyRepository.findMaxCodyImgNum();
                    renameFileName = uploadImage(file, saveCount);
                    saveCount ++;
                    CodyImg codyImg = CodyImg.builder()
                            .codyNum(myCodyNum)
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
        int count = 0;
        List<Goods> goodsList = new ArrayList<>();
        //상품 리스트
        if (goodsAttachment != null) {
            String renameFileName = null;
            for (MultipartFile file : goodsAttachment) {
                try {
                    Long maxGoodsNum = goodsRepository.findMaxGoodsNumber();
                    renameFileName = uploadImage(file, saveCount);
                    saveCount ++;
                    goodsDto.get(count).setGoodsImg(file.getOriginalFilename());
                    goodsDto.get(count).setGoodsReImg(renameFileName);

                    Goods goods = Goods.builder()
                            .goodsNum(maxGoodsNum == null ? 1 : maxGoodsNum + 1)
                            .codyNum(myCodyNum)
                            .brandName(goodsDto.get(count).getBrandName())
                            .goodsName(goodsDto.get(count).getGoodsName())
                            .goodsPrice(goodsDto.get(count).getGoodsPrice())
                            .goodsSize(goodsDto.get(count).getGoodsSize())
                            .goodsLink(goodsDto.get(count).getGoodsLink())
                            .goodsImg(file.getOriginalFilename())
                            .goodsReImg(renameFileName)
                            .build();

                    goodsRepository.save(goods);

                    goodsList.add(goods);
                    count ++;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        System.out.println("cody.getModelReImg() : " + cody.getModelReImg());

        codyDto = CodyDto.builder()
                .codyNum(myCodyNum)
                .codyImgs(codyImgs)
                .goodsList(goodsList)
                .build();


        return codyDto;
    }

    public String uploadImage(MultipartFile file, int saveCount) throws Exception {
        String originalFileName = file.getOriginalFilename();
        String renameFileName = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis()));
        renameFileName += "_" + saveCount;
        renameFileName += originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

        String savePath = System.getProperty("user.dir") + "/src/main/resources/cody_upload/";
        File saveFile = new File(savePath, renameFileName);
        file.transferTo(saveFile);

        return renameFileName;
    }


    public void deleteCody(Long codyNum) {
        Cody cody = codyRepository.findById(codyNum).get();
        cody.setDeleteDate(LocalDateTime.now());

        codyRepository.save(cody);
    }

    public void deleteCodyImg(Long codyImgNum) {
        CodyImg codyImg = codyImgRepository.findById(codyImgNum).get();
        codyImg.setDeleteDate(LocalDateTime.now());

        codyImgRepository.save(codyImg);
    }

    public void deleteGoods(Long goodsNum) {
        /*
        Goods goods = goodsRepository.findById(goodsNum).orElse(null);
        if (goods != null) {
            String savePath = System.getProperty("user.dir") + "/src/main/resources/cody_upload/";
            File deleteFile = new File(savePath + goods.getGoodsReImg());
            deleteFile.delete();
            goodsRepository.delete(goods);
        }
        */
        Goods goods = goodsRepository.findById(goodsNum).get();
        goods.setDeleteDate(LocalDateTime.now());

        goodsRepository.save(goods);
    }

}
