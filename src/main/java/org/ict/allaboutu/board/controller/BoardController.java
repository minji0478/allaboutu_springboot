package org.ict.allaboutu.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.service.BoardDto;
import org.ict.allaboutu.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardDto>> getBoardList() throws Exception {
        List<BoardDto> list = boardService.getBoardList();

//        Page<BoardDto> boardPage = boardService.getBoardList(pageable);
//        List<BoardDto> boardList = boardPage.getContent();
        log.info("getBoardList 결과 : " + list);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{boardNum}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long boardNum) throws Exception {
        Board board = boardService.getBoardById(boardNum);
        return ResponseEntity.ok(board);
    }
//
//    @PostMapping
//    public ResponseEntity<Board> createBoard(@RequestBody Board board) throws Exception {
//        return boardService.createBoard(board);
//    }
//
//    @PatchMapping("/{boardNum}")
//    public ResponseEntity<Board> updateBoard(@PathVariable Long boardNum, @RequestBody Board board) throws Exception {
//        return boardService.updateBoard(boardNum, board);
//    }
//
//    @DeleteMapping("/{boardNum}")
//    public ResponseEntity<Objects> deleteBoard(@PathVariable Long boardNum) throws Exception {
//        return boardService.deleteBoard(boardNum);
//    }
//
//    @GetMapping("/{boardNum}/comments")
//    public ResponseEntity<List<Comment>> getCommentList(@PathVariable Long boardNum) throws Exception {
//        return boardService.getCommentList(boardNum);
//    }
//
//    @PostMapping("/{boardNum}/comments")
//    public ResponseEntity<Comment> createComment(@PathVariable Long boardNum, @RequestBody Comment comment) throws Exception {
//        return boardService.createComment(boardNum, comment);
//    }
//
//    @PatchMapping("/{boardNum}/comments/{commentNum}")
//    public ResponseEntity<Comment> updateComment(@PathVariable Long boardNum, @PathVariable Long commentNum, @RequestBody Comment comment) throws Exception {
//        return boardService.updateComment(boardNum, commentNum, comment);
//    }
//
//    @DeleteMapping("/{boardNum}/comments/{commentNum}")
//    public ResponseEntity<Void> deleteComment(@PathVariable Long boardNum, @PathVariable Long commentNum) throws Exception {
//        return boardService.deleteComment(boardNum, commentNum);
//    }
//
//    @GetMapping("/{boardNum}/likes/{userNum}")
//    public ResponseEntity<Board> getLike(@PathVariable Long boardNum, @PathVariable Long userNum) throws Exception {
//        return boardService.getLike(boardNum, userNum);
//    }
//
//    @PostMapping("/{boardNum}/likes/{userNum}")
//    public ResponseEntity<Board> createLike(@PathVariable Long boardNum, @PathVariable Long userNum) throws Exception {
//        return boardService.createLike(boardNum, userNum);
//    }
//
//    @PatchMapping("/{boardNum}/likes/{userNum}")
//    public ResponseEntity<Void> deleteLike(@PathVariable Long boardNum, @PathVariable Long userNum) throws Exception {
//        return boardService.deleteLike(boardNum, userNum);
//    }
//
//    @GetMapping("/search/{hashtag}")
//    public ResponseEntity<List<Board>> getBoardListByHashtag(@PathVariable String hashtag) throws Exception {
//        return boardService.getBoardListByHashtag(hashtag);
//    }

}
