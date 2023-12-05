package org.ict.allaboutu.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.service.BoardDto;
import org.ict.allaboutu.board.service.BoardService;
import org.ict.allaboutu.common.FileNameChange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
@PropertySource("classpath:/application.properties")
public class BoardController {

    private final BoardService boardService;

    @Value("${board_upload.path}")
    private String uploadPath;

    @GetMapping
    public ResponseEntity<Page<BoardDto>> getBoardList(
            @PageableDefault(sort = {"boardNum"}) Pageable pageable
    ) throws Exception {
        pageable = PageRequest.of(0, 4, Sort.by("boardNum").descending());
        Page<BoardDto> list = boardService.getBoardList(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{boardNum}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long boardNum) throws Exception {
        Board board = boardService.getBoardById(boardNum);
        return ResponseEntity.ok(board);
    }

    @PostMapping
    public ResponseEntity<Board> createBoard(
            @ModelAttribute BoardDto boardDto,
//            @RequestPart(value = "hashtags", required = false) List<BoardHashtag> hashtags,
            @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments
    ) throws Exception {
        System.out.println("<<<<<<<<<<==============================>>>>>>>>>>");
        System.out.println("POST /boards: boardDto=" + boardDto);
        System.out.println("POST /boards: hashtags=" + boardDto.getHashtags());
        System.out.println("POST /boards: attachments=" + attachments);
        System.out.println("<<<<<<<<<<==============================>>>>>>>>>>");

        if (attachments != null) {
            String uploadPath = "static/board_upload";
            ClassPathResource resource = new ClassPathResource("static/board_upload");
            String absolutePath = resource.getFile().getAbsolutePath();

            for (MultipartFile file : attachments) {
                // 이미지 파일만 업로드
                if (!Objects.requireNonNull(file.getContentType()).startsWith("image")) {
                    log.warn("this file is not image type");
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }

                String originalFileName = file.getOriginalFilename();
                String renameFileName = FileNameChange.change(originalFileName, "yyyyMMddHHmmss");
                String saveName = uploadPath + File.separator + renameFileName;
                Path savePath = Paths.get(absolutePath, renameFileName);
                System.out.println(savePath);

                try {
                    file.transferTo(new File(renameFileName));
                    System.out.println(originalFileName + " 파일 업로드 성공 - renameFileName=" + renameFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return ResponseEntity.ok(boardService.createBoard(boardDto));
    }

    @DeleteMapping("/{boardNum}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardNum) throws Exception {
        boardService.deleteBoard(boardNum);
        return ResponseEntity.noContent().build();
    }

}
