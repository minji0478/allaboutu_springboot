package org.ict.allaboutu.personalcolor.service;

import lombok.RequiredArgsConstructor;
import org.ict.allaboutu.personalcolor.repository.PersonalColorRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonalColorService {
    private final PersonalColorRepository repository;

}
