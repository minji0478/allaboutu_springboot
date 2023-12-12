package org.ict.allaboutu.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.BoardLike;
import org.ict.allaboutu.board.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards/{boardNum}/likes/{userId}")
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<Boolean> isLiked(
            @PathVariable("boardNum") Long boardNum,
            @PathVariable("userId") String userId
    ) throws Exception {
        BoardLike like = likeService.isLiked(boardNum, userId);

        boolean result = (like != null) ? true : false;
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<BoardLike> createLike(
            @PathVariable("boardNum") Long boardNum,
            @PathVariable("userId") String userId
    ) throws Exception {
        BoardLike like = likeService.createLike(boardNum, userId);
        return ResponseEntity.ok(like);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLike(
            @PathVariable("boardNum") Long boardNum,
            @PathVariable("userId") String userId
    ) throws Exception {
        likeService.deleteLike(boardNum, userId);
        return ResponseEntity.noContent().build();
    }

}
