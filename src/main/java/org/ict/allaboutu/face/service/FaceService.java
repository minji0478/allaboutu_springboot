package org.ict.allaboutu.face.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.face.repository.FaceRepository;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FaceService {

    private final FaceRepository faceRepository;
}
