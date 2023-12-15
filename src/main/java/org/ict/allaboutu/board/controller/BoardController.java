package org.ict.allaboutu.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.admin.domain.Report;
import org.ict.allaboutu.admin.service.ReportDto;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.service.BoardDto;
import org.ict.allaboutu.board.service.BoardService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    // 게시글 목록 조회
    @GetMapping
    @CrossOrigin(origins = "http://localhost:2222")
    public ResponseEntity<Page<BoardDto>> getBoardList(@PageableDefault(page = 0, size = 4) Pageable pageable) throws Exception {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("boardNum").descending());
        Page<BoardDto> list = boardService.getBoardList(pageable);
        return ResponseEntity.ok(list);
    }

    // 게시글 상세 조회
    @GetMapping("/{boardNum}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable Long boardNum) throws Exception {
        return ResponseEntity.ok(boardService.getBoardById(boardNum));
    }

    // 게시글 랭킹 조회
    @GetMapping("/rank")
    public ResponseEntity<Page<BoardDto>> getBoardRank() throws Exception {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(
                Sort.Order.desc("readCount")
        ).descending());
        return ResponseEntity.ok(boardService.getBoardRank(pageable));
    }

    // 해시태그 검색
    @GetMapping("/search")
    public ResponseEntity<Page<BoardDto>> searchBoard(
            @RequestParam("keyword") String keyword,
            @PageableDefault(page = 0, size = 4) Pageable pageable
    ) throws Exception {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("boardNum").descending());
        keyword = URLDecoder.decode(keyword, "UTF-8");
        System.out.println("keyword: " + keyword);
        return ResponseEntity.ok(boardService.searchBoard(keyword, pageable));
    }

    // 게시글 등록
    @PostMapping
    public ResponseEntity<BoardDto> createBoard(
            @RequestPart("board") BoardDto boardDto,
            @RequestPart(value = "hashtags", required = false) List<String> hashtagList,
            @RequestPart(value = "attachments", required = false) List<MultipartFile> files
    ) throws Exception {
        return ResponseEntity.ok(boardService.createBoard(boardDto, hashtagList, files));
    }

    @PatchMapping("/{boardNum}")
    public ResponseEntity<BoardDto> updateBoard(
            @RequestPart("board") Board board,
            @RequestPart(value = "hashtags", required = false) List<String> hashtagList,
            @RequestPart(value = "attachments", required = false) List<MultipartFile> files
    ) throws Exception {
        return ResponseEntity.ok(boardService.updateBoard(board, hashtagList, files));
    }

    // 게시글 삭제
    @DeleteMapping("/{boardNum}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardNum) throws Exception {
        boardService.deleteBoard(boardNum);
        return ResponseEntity.noContent().build();
    }

    // 이미지 가져오기
    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws Exception {
        Resource resource = new FileSystemResource("E:\\poketAi_workspace\\allaboutu_springboot\\src\\main\\resources\\board_upload\\" + imageName);

        File file = resource.getFile();
        MediaType mediaType = getContentType(file.getName().substring(file.getName().lastIndexOf(".") + 1));
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(new InputStreamResource(resource.getInputStream()));
    }

    // 게시글 신고
    @PostMapping("/{boardNum}/reports")
    public ResponseEntity<Report> reportBoard(
            @PathVariable Long boardNum,
            @RequestBody ReportDto reportDto
    ) throws Exception {
        System.out.println("reportDto: " + reportDto);
        return ResponseEntity.ok(boardService.reportBoard(reportDto));
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
}
