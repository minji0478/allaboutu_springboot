package org.ict.allaboutu.personalcolor.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.common.FileNameChange;
import org.ict.allaboutu.personalcolor.domain.UserPersonalColor;
import org.ict.allaboutu.personalcolor.service.PersonalColorService;
import org.ict.allaboutu.personalcolor.service.PersonalDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/personal")
public class PersonalColorController {

    private final PersonalColorService personalColorService;

    // UserPersonalColor 에 첨부파일 업로드 메소드
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String[]> uploadImage(@RequestParam("file") MultipartFile file) {
        
        log.info("upload 요청왔다");
        log.info("file : " + file);
        String savePath = System.getProperty("user.dir") + "src/main/resources/personal_upload";
        String originalFileName = null;
        String renameFileName = null;

        if (file != null) {
            // 이미지 파일만 업로드
            if(!Objects.requireNonNull(file.getContentType()).startsWith("image")){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        originalFileName = file.getOriginalFilename();
        renameFileName = FileNameChange.change(originalFileName, "yyyyMMddHHmmss");

        log.info("personal savePath : " + savePath);

        try {
            File saveFile = new File(savePath + renameFileName);
            file.transferTo(saveFile);
        }catch (IOException e) {
            e.printStackTrace();
        }

        String [] urlArr = {savePath, originalFileName, renameFileName};
        return new ResponseEntity<>(urlArr, HttpStatus.OK);
    }

    @PostMapping(value = "/insert", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserPersonalColor> uploadImage(@ModelAttribute PersonalDto personalDto) {
        log.info("insert 요청왔다");
        return ResponseEntity.ok(personalColorService.insertPersonal(personalDto));
    }
}
