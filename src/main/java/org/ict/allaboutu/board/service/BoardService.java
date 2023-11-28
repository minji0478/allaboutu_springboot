package org.ict.allaboutu.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<BoardDto> getBoardList(Pageable pageable) {
        return null;
    }

    public Board getBoardById(Long boardNum) {
        return null;
    }

    public Board createBoard(Board board) {
        return null;
    }

    public Board updateBoard(Long boardNum, Board board) {
        return null;
    }

    public void deleteBoard(Long boardNum) {
        return null;
    }

}
