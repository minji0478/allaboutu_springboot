package org.ict.allaboutu.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.service.MemberService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class MemberController {

//    @PostMapping("/login")
//    public String loginMethod(@RequestBody Member member) {
//        log.info("/login : " + member.toString());
//        return null;
//    }
    @GetMapping("/member/image/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws Exception {
        Resource resource = new ClassPathResource("/user_profile/" + imageName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(resource.getInputStream()));
    }
}
