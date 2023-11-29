package org.ict.allaboutu.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.repository.BoardRepository;
import org.ict.allaboutu.board.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public List<BoardDto> getBoardList() {
        List<Board> boardEntities = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board entity : boardEntities) {
            BoardDto boardDto = BoardDto.builder()
                    .boardNum(entity.getBoardNum())
                    .userNum(entity.getUserNum())
                    .categoryNum(entity.getCategoryNum())
                    .boardTitle(entity.getBoardTitle())
                    .boardContent(entity.getBoardContent())
                    .createDate(entity.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                    .modifyDate((entity.getModifyDate() != null) ? entity.getModifyDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) : "N/A")
                    .readCount(entity.getReadCount())
                    .build();
            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }

    public Board getBoardById(long boardNum) throws Exception {

        return boardRepository.findById(boardNum).get();
    }

//    public Board getBoardById(Long boardNum) {
//        return null;
//    }
//
//    public Board createBoard(Board board) {
//        return null;
//    }
//
//    public Board updateBoard(Long boardNum, Board board) {
//        return null;
//    }
//
//    public void deleteBoard(Long boardNum) {
//
//    }

}
