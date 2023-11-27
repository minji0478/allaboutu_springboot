package org.ict.allaboutu.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/boards/{boardNum}/comments")
public class CommentController {

    @GetMapping("/{boardNum}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long boardNum) throws Exception {
        return boardService.getComments(boardNum);
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
    public ResponseEntity<Objects> deleteComment(@PathVariable Long boardNum, @PathVariable Long commentNum) throws Exception {
        return boardService.deleteComment(boardNum, commentNum);
    }

}
