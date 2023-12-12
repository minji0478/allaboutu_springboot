package org.ict.allaboutu.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.domain.BoardLike;
import org.ict.allaboutu.board.domain.BoardLikePK;
import org.ict.allaboutu.board.repository.LikeRepository;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;

    public BoardLike isLiked(Long boardNum, Long userNum) throws Exception {
        Member member = memberRepository.findById(userNum).orElse(null);

        if (member == null) {
            return null;
        } else {
            return likeRepository.findById(new BoardLikePK(boardNum, userNum)).orElse(null);
        }
    }

    public BoardLike createLike(Long boardNum, Long userNum) throws Exception {
        Member member = memberRepository.findById(userNum).orElse(null);

        if (member == null) {
            return null;
        } else {
            BoardLikePK id = new BoardLikePK(boardNum, userNum);
            BoardLike like = new BoardLike(id, LocalDateTime.now());

            return likeRepository.save(like);
        }
    }

    public void deleteLike(Long boardNum, Long userNum) throws Exception {
        Member member = memberRepository.findById(userNum).orElse(null);

        if (member == null) {
            return;
        } else {
            BoardLikePK id = new BoardLikePK(boardNum, member.getUserNum());
            BoardLike like = likeRepository.findById(id).orElse(null);

            if (like != null) {
                likeRepository.delete(like);
            }
        }
    }

}
