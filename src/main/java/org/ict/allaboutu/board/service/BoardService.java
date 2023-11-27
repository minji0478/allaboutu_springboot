package org.ict.allaboutu.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.repository.BoardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;


    public List<Board> getBoards() {

    }

    public Board getBoardById(Long boardNum) {
    }

    public Board createBoard(Board board) {
    }

    public Board updateBoard(Long boardNum, Board board) {
    }

    public void deleteBoard(Long boardNum) {
    }

    public Board createLike(Long boardNum, Long userNum) {
    }

    public Board updateLike(Long boardNum, Long userNum) {
    }

    public List<Board> getBoardsByHashtag(String hashtag) {
    }

}
