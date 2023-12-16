package org.ict.allaboutu.website.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.myPage.service.MyPageService;
import org.ict.allaboutu.notice.domain.Notice;
import org.ict.allaboutu.website.domain.Website;
import org.ict.allaboutu.website.service.WebsiteDto;
import org.ict.allaboutu.website.service.WebsiteService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.net.URLDecoder;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class WebsiteController {

    private final WebsiteService websiteService;

    @GetMapping("/websites/{webNum}")
    public ResponseEntity<WebsiteDto> getWeb(@PathVariable Long webNum) {
        System.out.println("userNum : " + webNum);
        WebsiteDto websiteDto = websiteService.getWeb(webNum);
        return ResponseEntity.ok(websiteDto);
    }

    @GetMapping("/websites")
    public ResponseEntity<Page<Website>> getWebPage(@PageableDefault(page=0,size = 8)Pageable pageable) {
        Page<Website> page = websiteService.getWebPage(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/websites")
    public ResponseEntity<Website> postWeb(@RequestPart("website") Website postWebSite,
                                           @RequestPart(value = "file") MultipartFile file){
        Website website = websiteService.postWeb(postWebSite, file);
        return ResponseEntity.ok(website);
    }

    @PatchMapping("/websites/{webNum}")
    public ResponseEntity<Website> patchWeb(
            @PathVariable Long webNum,
            @RequestPart("website") Website patchWebsite,
            @RequestPart(value = "file", required = false) MultipartFile file){

        patchWebsite.setWebNum(webNum);
        Website website = websiteService.patchWeb(patchWebsite, file);
        return ResponseEntity.ok(website);
    }

    @DeleteMapping("/websites/{webNum}")
    public ResponseEntity<Void> deleteWeb(@PathVariable Long webNum) {
        websiteService.deleteWeb(webNum);

        return ResponseEntity.noContent().build();  //void 리턴
    }

    @GetMapping("/websites/image/{webImg}")
    public ResponseEntity<Resource> getImage(@PathVariable String webImg) throws Exception {
        Resource resource = new FileSystemResource("E:\\poketAi_workspace\\allaboutu_springboot\\src\\main\\resources\\website_pics\\" + webImg);

        File file = resource.getFile();
        MediaType mediaType = getContentType(file.getName().substring(file.getName().lastIndexOf(".") + 1));
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(new InputStreamResource(resource.getInputStream()));
    }

    private MediaType getContentType(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "bmp":
                return MediaType.parseMediaType("image/bmp");
            case "webp":
                return MediaType.parseMediaType("image/webp");
            case "svg":
                return MediaType.parseMediaType("image/svg+xml");
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

}
