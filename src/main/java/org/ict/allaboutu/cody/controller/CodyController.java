package org.ict.allaboutu.cody.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.cody.service.CodyDto;
import org.ict.allaboutu.cody.service.CodyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cody")
public class CodyController {
    private final CodyService codyService;

    @GetMapping
    @CrossOrigin(origins = "http://localhost:2222")
    public ResponseEntity<Page<CodyDto>> getCodyList(@PageableDefault(page = 0, size = 12) Pageable pageable) throws Exception {

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("codyNum").descending());
        System.out.println("pageable: " + pageable);
        Page<CodyDto> list = codyService.getCodyList(pageable);
        System.out.println("list: " + list);
        return ResponseEntity.ok(list);
    }

}
