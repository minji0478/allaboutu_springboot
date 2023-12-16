package org.ict.allaboutu.website.service;


import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.common.FileNameChange;
import org.ict.allaboutu.website.domain.Website;
import org.ict.allaboutu.website.repository.WebsiteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebsiteService {

    private final WebsiteRepository websiteRepository;


    public WebsiteDto getWeb(Long webNum){
        Website website = websiteRepository.findById(webNum).get();

        return WebsiteDto.builder()
                .webNum(website.getWebNum())
                .webImg(website.getWebImg())
                .brand(website.getBrand())
                .webTitle(website.getWebTitle())
                .price(website.getPrice())
                .webLink(website.getWebLink())
                .build();
    }

    public Page<Website> getWebPage(Pageable pageable){
        Page<Website> websitePage = websiteRepository.findAll(pageable);

        return  websitePage;
    }

    public Website postWeb(Website website, MultipartFile file){
        Long MaxNum = websiteRepository.findMaxWebNum();
        if (MaxNum == null){
            website.setWebNum(1L);
        }
        else {
            website.setWebNum(MaxNum + 1L);
        }
        if(file != null){
            try{
                String savePath = System.getProperty("user.dir") + "/src/main/resources/website_pics/";
                File saveFile = new File(savePath, file.getOriginalFilename());
                file.transferTo(saveFile);
                website.setWebImg(file.getOriginalFilename());
            }catch(Exception e) {
                e.printStackTrace();
            }

        }

        return websiteRepository.save(website);

    }

    public void deleteWeb(Long webNum){
        Website website = websiteRepository.findById(webNum).get();
        if (website != null){
            websiteRepository.delete(website);
        }
    }

    @Transactional    //patch 할때만 필요함
    public Website patchWeb(Website website, MultipartFile file){
        Website oldWebsite = websiteRepository.findById(website.getWebNum()).get();

        if (file != null){
            if (!file.getOriginalFilename().equals(oldWebsite.getWebImg())){
                if (oldWebsite.getWebImg() != null){
                    String savePath = System.getProperty("user.dir") + "/src/main/resources/website_pics/";
                    File deleteFile = new File(savePath, oldWebsite.getWebImg());
                    deleteFile.delete();
                }

                try {
                    String savePath = System.getProperty("user.dir") + "/src/main/resources/website_pics/";
                    File saveFile = new File(savePath, file.getOriginalFilename());
                    file.transferTo(saveFile);
                    website.setWebImg(file.getOriginalFilename());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // 이미지 변경이 없는 경우 기존 값을 그대로 사용
                website.setWebImg(oldWebsite.getWebImg());
            }
        } else {
            // 첨부 파일을 삭제했을 경우, 저장된 파일 삭제
            String savePath = System.getProperty("user.dir") + "/src/main/resources/website_pics/";
            File deleteFile = new File(savePath, oldWebsite.getWebImg());
            deleteFile.delete();

            // DB에 File 이름을 null로 저장
            website.setWebImg(null);
        }


        return websiteRepository.save(website);

    }
}
