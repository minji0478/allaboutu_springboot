package org.ict.allaboutu.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.BoardLike;
import org.ict.allaboutu.board.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public BoardLike isLiked(Long boardNum, Long userNum) throws Exception {
        return likeRepository.findByBoardNumAndUserNum(boardNum, userNum);
    }

    public BoardLike createLike(Long boardNum, Long userNum) throws Exception {
        BoardLike like = BoardLike.builder()
                .likeNum(likeRepository.findMaxLikeNum() + 1)
                .boardNum(boardNum)
                .userNum(userNum)
                .createDate(LocalDateTime.now())
                .checked("Y")
                .build();

        return likeRepository.save(like);
    }

    public void deleteLike(Long boardNum, Long userNum) throws Exception {
        BoardLike like = likeRepository.findByBoardNumAndUserNum(boardNum, userNum);
        likeRepository.delete(like);
    }

}
