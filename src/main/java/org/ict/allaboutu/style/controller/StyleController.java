package org.ict.allaboutu.style.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.common.FileNameChange;
import org.ict.allaboutu.style.domain.Style;
import org.ict.allaboutu.style.service.RequestDto;
import org.ict.allaboutu.style.service.StyleDto;
import org.ict.allaboutu.style.service.StyleService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/style")
public class StyleController {

    private final StyleService styleService;

    //사진 업로드 폴더에 넣고 그 사진으로 파이썬에 요청해야 함
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String[]> uploadImage(@RequestParam("file") MultipartFile file) {
        String savePath = System.getProperty("user.dir") + "/src/main/resources/style_upload/";
        String originalFileName = null;
        String renameFileName = null;

        if (file != null) {

            // 이미지 파일만 업로드
            if (!Objects.requireNonNull(file.getContentType()).startsWith("image")) {
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            originalFileName = file.getOriginalFilename();
            renameFileName = FileNameChange.change(originalFileName, "yyyyMMddHHmmss");
            System.out.println("originalFileName : " + originalFileName);
            System.out.println("renameFileName : " + renameFileName);

            try {
                File saveFile = new File(savePath + renameFileName);
                file.transferTo(saveFile);

                System.out.println(originalFileName + " 파일 업로드 성공 - renameFileName=" + renameFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        String [] urlArr = {savePath, originalFileName, renameFileName};
        return new ResponseEntity<>(urlArr, HttpStatus.OK);
    }

    //다끝나고 파일 인서트 하기
    @PostMapping("/insert")
    public ResponseEntity<Style> insertUserStyle(@RequestBody StyleDto styleDto) {
        System.out.println("styleDto : " + styleDto);
        return ResponseEntity.ok(styleService.insertStyle(styleDto));
    }
    // 이미지 가져오기

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

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws Exception {

        Resource resource = null;
        int retryCount = 0;
        boolean imageFound = false;

        int RETRY_DELAY = 500;
        int MAX_RETRY_COUNT = 500;
        System.out.println("===imageName : " + imageName);
        while (!imageFound && retryCount < MAX_RETRY_COUNT) {
            try {
                resource = new FileSystemResource("E:\\poketAi_workspace\\allaboutu_springboot\\src\\main\\resources\\style_upload\\" + imageName);
                System.out.println("retryCount : " + retryCount);
                if (resource.exists()) {
                    imageFound = true;
                }
            } catch (Exception e) {
                // Handle exception, if needed
            }

            if (!imageFound) {
                // Wait for a certain period before retrying
                Thread.sleep(RETRY_DELAY);
                retryCount++;
            }
        }
        System.out.println("===imageFound : " + imageFound);
        if (imageFound) {
            File file = resource.getFile();
            MediaType mediaType = getContentType(file.getName().substring(file.getName().lastIndexOf(".") + 1));
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(new InputStreamResource(resource.getInputStream()));

        } else {
            // Image not found, return an appropriate response
            return ResponseEntity.notFound().build();
        }
    }


}
