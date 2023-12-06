package org.ict.allaboutu.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.admin.domain.Report;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.service.BoardDto;
import org.ict.allaboutu.board.service.BoardService;
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
import org.springframework.web.multipart.MultipartFile;

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
        System.out.println("pageable: " + pageable);
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("boardNum").descending());
        System.out.println("pageable: " + pageable);
        Page<BoardDto> list = boardService.getBoardList(pageable);
        return ResponseEntity.ok(list);
    }

    // 게시글 상세 조회
    @GetMapping("/{boardNum}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long boardNum) throws Exception {
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


    // 게시글 등록
    @PostMapping
    public ResponseEntity<BoardDto> createBoard(
            @RequestPart("board") Board board,
            @RequestPart(value = "hashtags", required = false) List<String> hashtagList,
            @RequestPart(value = "attachments", required = false) List<MultipartFile> files
    ) throws Exception {
        return ResponseEntity.ok(boardService.createBoard(board, hashtagList, files));
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
        Resource resource = new ClassPathResource("/board_upload/" + imageName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(resource.getInputStream()));
    }

    // 게시글 신고
    @PostMapping("/{boardNum}/reports")
    public ResponseEntity<Report> reportBoard(
            @PathVariable Long boardNum,
            @RequestBody Report report
    ) throws Exception {
        return ResponseEntity.ok(boardService.reportBoard(report));
    }
}
