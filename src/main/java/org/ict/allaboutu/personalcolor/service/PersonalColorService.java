package org.ict.allaboutu.personalcolor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.personalcolor.domain.UserPersonalColor;
import org.ict.allaboutu.personalcolor.repository.PersonalColorRepository;
import org.ict.allaboutu.personalcolor.repository.UserpersonalColorRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@RequiredArgsConstructor
@Service
@PropertySource("classpath:application.properties")
public class PersonalColorService {
    private final PersonalColorRepository personalColorRepository;
    private final UserpersonalColorRepository userpersonalColorRepository;

    @Value("${img.upload-dir}")
    private String uploadDir;

    public String uploadImage(MultipartFile file)throws Exception{
        Long maxPersonalNum = userpersonalColorRepository.findPersonalNum();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(fileName);
        String newFileName = generateNewFileName(fileExtension);
        Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(newFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString();
    }

    // UserPersonalColor 에 첨부파일 업로드 메소드
    public UserPersonalColor insertPersonal(PersonalDto personalDto) {
        // 퍼스널 컬러의 가장 큰값을 찾아 거기에 1 더한값을 넣기 위해 처리함!!
        Long maxPersonalNum = userpersonalColorRepository.findPersonalNum();

        LocalDateTime date = LocalDateTime.now();
        UserPersonalColor userPersonalColor = UserPersonalColor.builder()
                .personalUserNum(maxPersonalNum == null ? 1 : maxPersonalNum + 1)
                .userNum(personalDto.getUserNum())
                .personalDate(date)
                .personalImg(personalDto.getPersonalImg())
                .personalReimg(personalDto.getPersonalReimg())
                .personalNum(personalDto.getPersonalNum())
                .build();

        UserPersonalColor saveResult = userpersonalColorRepository.save(userPersonalColor);

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
