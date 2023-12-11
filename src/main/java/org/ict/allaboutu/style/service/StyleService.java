package org.ict.allaboutu.style.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.domain.BoardHashtag;
import org.ict.allaboutu.board.domain.BoardHashtagLink;
import org.ict.allaboutu.board.domain.BoardHashtagLinkPK;
import org.ict.allaboutu.board.service.BoardDto;
import org.ict.allaboutu.style.domain.Style;
import org.ict.allaboutu.style.service.StyleDto;
import org.ict.allaboutu.style.repository.StyleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
@PropertySource("classpath:application.properties")
public class StyleService {
    private final StyleRepository styleRepository;

    @Value("${img.upload-dir}")
    private String uploadDir;

    public String uploadImage(MultipartFile file) throws Exception {
        Long maxStyleNum = styleRepository.findMaxStyleNum();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(fileName);
        String newFileName = generateNewFileName(fileExtension);
        log.info("fileName : " + fileName);
        log.info("newFileName : " + newFileName);
        Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(newFileName);
        log.info("filePath : " + filePath);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        log.info("filePath.toString() : " + filePath.toString());
        return filePath.toString();
    }

    public Style insertStyle(StyleDto styleDto) {
        Long maxStyleNum = styleRepository.findMaxStyleNum();
        System.out.println("maxStyleNum : " + maxStyleNum);

        Style style = Style.builder()
                .styleNum(maxStyleNum == null || maxStyleNum == 0 ? 1 : maxStyleNum + 1)
                .userNum(styleDto.getUserNum())
                .formNum(styleDto.getFormNum())
                .userImg(styleDto.getUserImg())
                .userReimg(styleDto.getUserReimg())
                .userStyle(styleDto.getUserStyle())
                .userRestyle(styleDto.getUserRestyle())
                .height(styleDto.getHeight())
                .weight(styleDto.getWeight())
                .shoulder(styleDto.getShoulder())
                .waist(styleDto.getWaist())
                .arm(styleDto.getArm())
                .leg(styleDto.getLeg())
                .styleDate(styleDto.getStyleDate())
                .build();

        Style saveResult = styleRepository.save(style);
        return saveResult;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    private String generateNewFileName(String fileExtension) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return timestamp + "." + fileExtension;
    }

}
