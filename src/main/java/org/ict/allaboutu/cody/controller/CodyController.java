package org.ict.allaboutu.cody.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.cody.service.CodyDto;
import org.ict.allaboutu.cody.service.CodyService;
import org.springframework.core.io.FileSystemResource;
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

import java.io.File;

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
        Resource resource = new FileSystemResource("E:\\poketAi_workspace\\allaboutu_springboot\\src\\main\\resources\\cody_upload\\" + imageName);

        File file = resource.getFile();
        MediaType mediaType = getContentType(file.getName().substring(file.getName().lastIndexOf(".") + 1));
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(new InputStreamResource(resource.getInputStream()));
    }

    private MediaType getContentType(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "bmp":
                return MediaType.parseMediaType("image/bmp");
            case "webp":
                return MediaType.parseMediaType("image/webp");
            case "svg":
                return MediaType.parseMediaType("image/svg+xml");
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
//// 코디랑 코디이미지 추가해주는 컨트롤러 리턴을 두개 한번에 해야하는데 방법을 모르겠어요
//    @PostMapping
//    public ResponseEntity<CodyDto> createCody(
//        @RequestPart("cody") CodyDto codyDto,
//        @RequestPart(value = "codyimgs", required = false) List<MultipartFile> files,
//        @RequestPart(value = "cody", required = false) MultipartFile file
//    ) throws Exception {
//    CodyDto createCodyImg = codyService.createCodyImg(codyDto, files);
//
//    CodyDto createCody = codyService.createCody(codyDto, file);
//
//    return ResponseEntity.ok(createCodyImg);
//    }
//
//    // 상품 이미지 추가하는 컨트롤러
//    @PostMapping
//    public ResponseEntity<GoodsDto> createGoods(
//        @RequestPart("goods") CodyDto codyDto,
//        @RequestPart(value = "cody", required = false) MultipartFile file) throws Exception {
//
//    return ResponseEntity.ok(codyService.createGoods(codyDto, file));
//    }
//
//
//    // 코디이미지 삭제하는 컨트롤러
//    @DeleteMapping("/{codyNum}")
//    public ResponseEntity<Void> deleteCody(@PathVariable Long codyNum) throws Exception {
//        codyService.deleteCody(codyNum);
//        return ResponseEntity.noContent().build();
//    }
//
//    // 상품이미지 삭제하는 컨트롤러
//    @DeleteMapping("/{goodsNum}")
//    public ResponseEntity<Void> deleteGoods(@PathVariable Long goodsNum) throws Exception {
//        codyService.deleteGoods(goodsNum);
//        return ResponseEntity.noContent().build();
//    }
}
