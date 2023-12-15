//package org.ict.allaboutu.website.controller;
//
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.ict.allaboutu.myPage.service.MyPageService;
//import org.ict.allaboutu.website.domain.Website;
//import org.ict.allaboutu.website.service.WebsiteDto;
//import org.ict.allaboutu.website.service.WebsiteService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URLDecoder;
//
//@Slf4j
//@RestController
//@RequestMapping
//@RequiredArgsConstructor
//public class WebsiteController {
//
//    private final WebsiteService websiteService;
//
//    @GetMapping("/webSite/{webNum}")
//    public ResponseEntity<WebsiteDto> getWeb(@PathVariable Long webNum) {
//        System.out.println("userNum : " + webNum);
//        WebsiteDto websiteDto = websiteService.getWeb(webNum);
//        return ResponseEntity.ok(websiteDto);
//    }
//
//    @PostMapping("/webSite/{webNum}")
//    public ResponseEntity<WebsiteDto> postWeb(@PathVariable Long webNum, @RequestBody WebsiteDto postWebSite){
//        WebsiteDto websiteDto = websiteService.postWeb(webNum, postWebSite);
//        return ResponseEntity.ok(websiteDto);
//    }
//
//    @PatchMapping("/webSite/{webNum}")
//    public ResponseEntity<WebsiteDto> patchWeb(@PathVariable Long webNum, @RequestBody WebsiteDto patchWebSite){
//        WebsiteDto websiteDto = websiteService.patchWeb(webNum, patchWebSite);
//        return ResponseEntity.ok(websiteDto);
//    }
//
//    @DeleteMapping("/webSite/{webNum}")
//    public ResponseEntity<WebsiteDto> deleteWeb(@PathVariable Long webNum) {
//        WebsiteDto websiteDto = websiteService.deleteWeb(webNum);
//        return ResponseEntity.ok(websiteDto);
//    }
//
//}
