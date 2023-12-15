//package org.ict.allaboutu.website.service;
//
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.ict.allaboutu.website.domain.Website;
//import org.ict.allaboutu.website.repository.WebsiteRepository;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class WebsiteService {
//
//    private final WebsiteRepository websiteRepository;
//
//    @Transactional
//    public WebsiteDto getWeb(Long userNum){
//        Website website = websiteRepository.findOne(webNum);
//
//        return WebsiteDto.builder()
//                .webNum(website.getWebNum())
//                .webImg(website.getWebImg())
//                .brand(website.getBrand())
//                .webTitle(website.getWebTitle())
//                .price(website.getPrice())
//                .build();
//    }
//
//
//
//}
