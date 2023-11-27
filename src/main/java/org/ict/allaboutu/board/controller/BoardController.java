package org.ict.allaboutu.board.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.domain.Comment;
import org.ict.allaboutu.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<Board>> getBoards() throws Exception {
        return boardService.getBoards();
    }

    @GetMapping("/{boardNum}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long boardNum) throws Exception {
        return boardService.getBoardById(boardNum);
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board) throws Exception {
        return boardService.createBoard(board);
    }

    @PatchMapping("/{boardNum}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long boardNum, @RequestBody Board board) throws Exception {
        return boardService.updateBoard(boardNum, board);
    }

    @DeleteMapping("/{boardNum}")
    public ResponseEntity<Objects> deleteBoard(@PathVariable Long boardNum) throws Exception {
        return boardService.deleteBoard(boardNum);
    }

    @PostMapping("/{boardNum}/likes/{userNum}")
    public ResponseEntity<Board> createLike(@PathVariable Long boardNum, @PathVariable Long userNum) throws Exception {
        return boardService.createLike(boardNum, userNum);
    }

    @PatchMapping("/{boardNum}/likes/{userNum}")
    public ResponseEntity<Board> updateLike(@PathVariable Long boardNum, @PathVariable Long userNum) throws Exception {
        return boardService.updateLike(boardNum, userNum);
    }

    @GetMapping("/search/{hashtag}")
    public ResponseEntity<List<Board>> getBoardsByHashtag(@PathVariable String hashtag) throws Exception {
        return boardService.getBoardsByHashtag(hashtag);
    }

}
