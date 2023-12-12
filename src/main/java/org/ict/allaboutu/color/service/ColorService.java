package org.ict.allaboutu.color.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.color.repository.ColorRepository;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ColorService {

    private final ColorRepository colorRepository;

    // 등록 로직
    public ColorDto insertColor(ColorDto colorDto) {

        log.info("색상 정보를 저장합니다: {}", colorDto);

//        return colorRepository.save(colorDto);
        return null;
    }

    // 수정 로직
    public ColorDto updateColor(ColorDto colorDto) {
        // 필요한 비즈니스 로직 수행
        // colorDto에서 필요한 필드 값을 추출하여 적절한 처리 수행
        // 예를 들어, colorDto의 필드 값을 데이터베이스에서 조회하여 수정하고 수정된 결과를 반환하는 작업 등을 수행

        // 예시로 수정된 결과를 그대로 반환하는 로직을 작성
        return colorDto;
    }

    // 삭제 로직
    public void deleteColor(Long colorNum) {
        // 필요한 비즈니스 로직 수행
        // colorNum에 해당하는 데이터를 데이터베이스에서 삭제하는 작업 등을 수행
    }
}
