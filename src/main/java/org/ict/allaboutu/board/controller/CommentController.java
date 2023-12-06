package org.ict.allaboutu.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.service.CommentDto;
import org.ict.allaboutu.board.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards/{boardNum}/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentList(@PathVariable Long boardNum) throws Exception {
        List<CommentDto> list = commentService.getCommentList(boardNum);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable Long boardNum, @RequestBody CommentDto comment) throws Exception {
        return ResponseEntity.ok(commentService.createComment(boardNum, comment));
    }

    @PatchMapping("/{commentNum}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long boardNum, @PathVariable Long commentNum, @RequestBody CommentDto comment) throws Exception {
        return ResponseEntity.ok(commentService.updateComment(boardNum, commentNum, comment));
    }

    @DeleteMapping("/{commentNum}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentNum) throws Exception {
        commentService.deleteComment(commentNum);
        return ResponseEntity.noContent().build();
    }

}
