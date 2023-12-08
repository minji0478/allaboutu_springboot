package org.ict.allaboutu.website.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.myPage.service.MyPageService;
import org.ict.allaboutu.website.service.WebsiteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class WebsiteController {

    private WebsiteService websiteService;


}
