package org.ict.allaboutu.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class MemberController {

    @PostMapping("/login")
    public String loginMethod(@RequestBody Member member) {
        log.info("/login : " + member.toString());
        return null;
    }
}
