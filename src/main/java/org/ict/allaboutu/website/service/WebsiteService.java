package org.ict.allaboutu.website.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.website.repository.WebsiteRepository;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebsiteService {

    private final WebsiteRepository websiteRepository;


}
