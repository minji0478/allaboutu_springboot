package org.ict.allaboutu.board.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.domain.Comment;
import org.ict.allaboutu.board.service.BoardDto;
import org.ict.allaboutu.board.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<Board>> getBoardList(@RequestBody Pageable pageable) throws Exception {
        Page<BoardDto> boardPage = boardService.getBoardList(pageable);
        List<BoardDto> boardList = boardPage.getContent();

        return ResponseEntity.ok(boardList);
    }

    @GetMapping("/{boardNum}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long boardNum) throws Exception {
        return boardService.getBoardById(boardNum);
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody Board board) throws Exception {
        // 새로운 자원 생성 시에는 201 Created를 리턴
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

    @GetMapping("/{boardNum}/comments")
    public ResponseEntity<List<Comment>> getCommentList(@PathVariable Long boardNum) throws Exception {
        return boardService.getCommentList(boardNum);
    }

    @PostMapping("/{boardNum}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable Long boardNum, @RequestBody Comment comment) throws Exception {
        return boardService.createComment(boardNum, comment);
    }

    @PatchMapping("/{boardNum}/comments/{commentNum}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long boardNum, @PathVariable Long commentNum, @RequestBody Comment comment) throws Exception {
        return boardService.updateComment(boardNum, commentNum, comment);
    }

    @DeleteMapping("/{boardNum}/comments/{commentNum}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardNum, @PathVariable Long commentNum) throws Exception {
        return boardService.deleteComment(boardNum, commentNum);
    }

    @GetMapping("/{boardNum}/likes/{userNum}")
    public ResponseEntity<Board> getLike(@PathVariable Long boardNum, @PathVariable Long userNum) throws Exception {
        return boardService.getLike(boardNum, userNum);
    }

    @PostMapping("/{boardNum}/likes/{userNum}")
    public ResponseEntity<Board> createLike(@PathVariable Long boardNum, @PathVariable Long userNum) throws Exception {
        return boardService.createLike(boardNum, userNum);
    }

    @PatchMapping("/{boardNum}/likes/{userNum}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long boardNum, @PathVariable Long userNum) throws Exception {
        return boardService.deleteLike(boardNum, userNum);
    }

    @GetMapping("/search/{hashtag}")
    public ResponseEntity<List<Board>> getBoardListByHashtag(@PathVariable String hashtag) throws Exception {
        return boardService.getBoardListByHashtag(hashtag);
    }

}
