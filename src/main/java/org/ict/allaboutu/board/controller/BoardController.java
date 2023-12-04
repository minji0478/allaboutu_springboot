package org.ict.allaboutu.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.service.BoardDto;
import org.ict.allaboutu.board.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<Page<BoardDto>> getBoardList(
            @PageableDefault(sort = {"boardNum"}) Pageable pageable
    ) throws Exception {
        pageable = PageRequest.of(0, 4, Sort.by("boardNum").descending());
        Page<BoardDto> list = boardService.getBoardList(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{boardNum}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long boardNum) throws Exception {
        Board board = boardService.getBoardById(boardNum);
        return ResponseEntity.ok(board);
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody BoardDto board) throws Exception {
        return ResponseEntity.ok(boardService.createBoard(board));
    }

    @PostMapping("/images")
    public ResponseEntity<String> uploadImage(
            @RequestParam("attachments") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        System.out.println("Received file: " + fileName);

        // 이미지 저장
        boardService.uploadImage(file);

        return ResponseEntity.ok("이미지 업로드 성공");
    }

    @DeleteMapping("/{boardNum}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardNum) throws Exception {
        boardService.deleteBoard(boardNum);
        return ResponseEntity.noContent().build();
    }



}
