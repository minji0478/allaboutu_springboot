package org.ict.allaboutu.cody.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.cody.service.CodyDto;
import org.ict.allaboutu.cody.service.CodyService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cody")
public class CodyController {
    private final CodyService codyService;

    @GetMapping
    @CrossOrigin(origins = "http://localhost:2222")
    public ResponseEntity<Page<CodyDto>> getCodyList(@PageableDefault(page = 0, size = 12) Pageable pageable,
                                                     @RequestParam("formNum") Long formNum,
                                                     @RequestParam("currentPage") int currentPage,
                                                     @RequestParam("pageSize") int pageSize) throws Exception {
        System.out.println("formNum: " + formNum);
        System.out.println("currentPage: " + currentPage);
        System.out.println("pageSize: " + pageSize);
        pageable = PageRequest.of(currentPage-1, pageSize, Sort.by("codyNum").descending());
        System.out.println("pageable: " + pageable);
        Page<CodyDto> list = codyService.getCodyList(pageable, formNum);
        System.out.println("list: " + list.toString());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws Exception {
        Resource resource = new ClassPathResource("/cody_upload/" + imageName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(resource.getInputStream()));
    }
}
