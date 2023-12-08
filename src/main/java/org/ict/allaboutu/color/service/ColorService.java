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
}
