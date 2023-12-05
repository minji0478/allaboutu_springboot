package org.ict.allaboutu.style.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.service.BoardService;
import org.ict.allaboutu.style.service.StyleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/style")
public class StyleController {

    private final StyleService styleService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        log.info("file : " + file);
        try {
            String imageUrl = styleService.uploadImage(file);
            
            //업로드 성공하면 해당 사진으로 파이썬 실행 시켜야 함
            
            
            log.info("imageUrl : " + imageUrl);
            return new ResponseEntity<>(imageUrl, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
