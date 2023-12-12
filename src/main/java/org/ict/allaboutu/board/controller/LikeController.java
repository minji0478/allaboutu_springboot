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
@RequestMapping("/boards/{boardNum}/likes/{userNum}")
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<Boolean> isLiked(
            @PathVariable("boardNum") Long boardNum,
            @PathVariable("userNum") Long userNum
    ) throws Exception {
        BoardLike like = likeService.isLiked(boardNum, userNum);

        boolean result = (like != null) ? true : false;
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<BoardLike> createLike(
            @PathVariable("boardNum") Long boardNum,
            @PathVariable("userNum") Long userNum
    ) throws Exception {
        BoardLike like = likeService.createLike(boardNum, userNum);
        return ResponseEntity.ok(like);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLike(
            @PathVariable("boardNum") Long boardNum,
            @PathVariable("userNum") Long userNum
    ) throws Exception {
        likeService.deleteLike(boardNum, userNum);
        return ResponseEntity.noContent().build();
    }

}
