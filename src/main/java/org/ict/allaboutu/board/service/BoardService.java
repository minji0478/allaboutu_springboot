package org.ict.allaboutu.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.domain.Comment;
import org.ict.allaboutu.board.repository.BoardRepository;
import org.ict.allaboutu.board.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public Page<BoardDto> getBoardList(Pageable pageable) {
        List<BoardDto> boardDtoList = new ArrayList<>();

        Page<Board> boardEntities = boardRepository.findAll(pageable);
        Page<BoardDto> boardDtoPage = boardEntities.map(boardEntity -> {
            List<Comment> comments = commentRepository.findAllByBoardNum(boardEntity.getBoardNum());

            return BoardDto.builder()
                    .boardNum(boardEntity.getBoardNum())
                    .userNum(boardEntity.getUserNum())
                    .categoryNum(boardEntity.getCategoryNum())
                    .boardTitle(boardEntity.getBoardTitle())
                    .boardContent(boardEntity.getBoardContent())
                    .createDate(boardEntity.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                    .modifyDate((boardEntity.getModifyDate() != null) ? boardEntity.getModifyDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) : "N/A")
                    .readCount(boardEntity.getReadCount())
                    .comments(comments)
                    .build();
        });

        return boardDtoPage;
    }

    public List<BoardDto> getBoardList() {
        List<Board> boardEntities = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board entity : boardEntities) {
            List<Comment> comments = commentRepository.findAllByBoardNum(entity.getBoardNum());

            BoardDto boardDto = BoardDto.builder()
                    .boardNum(entity.getBoardNum())
                    .userNum(entity.getUserNum())
                    .categoryNum(entity.getCategoryNum())
                    .boardTitle(entity.getBoardTitle())
                    .boardContent(entity.getBoardContent())
                    .createDate(entity.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                    .modifyDate((entity.getModifyDate() != null) ? entity.getModifyDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")) : "N/A")
                    .readCount(entity.getReadCount())
                    .comments(comments)
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

    public Board createBoard(BoardDto boardDto) {
        Board board = Board.builder()
                .boardNum(boardRepository.findMaxBoardNum() == null ? 1 : boardRepository.findMaxBoardNum() + 1)
                .userNum(boardDto.getUserNum())
                .categoryNum(boardDto.getCategoryNum())
                .boardTitle(boardDto.getBoardTitle())
                .boardContent(boardDto.getBoardContent())
                .boardTitle(boardDto.getBoardTitle())
                .boardContent(boardDto.getBoardContent())
                .createDate(LocalDateTime.now())
                .readCount(0L)
                .build();
        return boardRepository.save(board);
    }

//    public Board updateBoard(Long boardNum, Board board) {
//        return null;
//    }
//
//    public void deleteBoard(Long boardNum) {
//
//    }

}
