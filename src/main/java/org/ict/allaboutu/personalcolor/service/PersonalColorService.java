package org.ict.allaboutu.personalcolor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.personalcolor.domain.UserPersonalColor;
import org.ict.allaboutu.personalcolor.repository.PersonalColorRepository;
import org.ict.allaboutu.personalcolor.repository.UserpersonalColorRepository;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@PropertySource("classpath:application.properties")
public class PersonalColorService {
    private final PersonalColorRepository personalColorRepository;
    private final UserpersonalColorRepository userpersonalColorRepository;
    // UserPersonalColor 에 첨부파일 업로드 메소드
    public UserPersonalColor insertPersonal(PersonalDto personalDto) {
        // 퍼스널 컬러의 가장 큰값을 찾아 거기에 1 더한값을 넣기 위해 처리함!!
        Long maxPersonalNum = userpersonalColorRepository.findPersonalNum();

        UserPersonalColor userPersonalColor = UserPersonalColor.builder()
                .personalUserNum(maxPersonalNum == null ? 1 : maxPersonalNum + 1)
                .userNum(personalDto.getUserNum())
                .personalNum(personalDto.getPersonalNum())
                .personalDate(personalDto.getPersonalDate())
                .personalImg(personalDto.getPersonalImg())
                .personalReimg(personalDto.getPersonalReimg())
                .build();

        UserPersonalColor saveResult = userpersonalColorRepository.save(userPersonalColor);

        return saveResult;
    }
}
