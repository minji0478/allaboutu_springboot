package org.ict.allaboutu.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.admin.domain.Admin;
import org.ict.allaboutu.admin.domain.Report;
import org.ict.allaboutu.admin.repository.AdminRepository;
import org.ict.allaboutu.admin.repository.ReportRepository;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.repository.BoardRepository;
import org.ict.allaboutu.board.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final ReportRepository reportRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    public Page<AdminDto> getMemberList(Pageable pageable) {
        List<AdminDto> dtos = new ArrayList<>();
        Page<Admin> adminEntites = adminRepository.findAll(pageable);

        Page<AdminDto> adminDtos = adminEntites.map(adminEntity -> {

            return AdminDto.builder()
                    .userId(adminEntity.getUserId())
                    .userEmail(adminEntity.getUserEmail())
                    .userPhone(adminEntity.getUserPhone())
                    .enrolleDate(adminEntity.getEnrollDate())
                    .account(adminEntity.getAccount())
                    .reportCount(adminEntity.getReportCount())
                    .build();

        });
//        log.info("adminDtos : " + adminDtos);
        return adminDtos;
    }
    public Page<AdminDto> getReportList(Pageable pageable) {
        List<AdminDto> dtos = new ArrayList<>();
        Page<Report> reportEntites = reportRepository.findAll(pageable);

        Page<AdminDto> adminDtos = reportEntites.map(reportEntity -> {
            return AdminDto.builder()
                    .boardNum(reportEntity.getBoardNum())
                    .reportNum(reportEntity.getReportNum())
                    .reportCause(reportEntity.getReportCause())
                    .reportReason(reportEntity.getReportReason())
                    .build();
        });

        return adminDtos;
    }

//    public Board getReportDetailList(Long boardNum) throws Exception {
//        Board board = Board.builder()
//        return boardRepository.getOne(boardNum);
//    }

    public void deleteDateBoard(Board board) throws Exception {
        Board boardEntity = boardRepository.getOne(board.getBoardNum());
        log.info("boardEntity : " + boardEntity);
//        LocalDateTime dateFormat = LocalDateTime.now();
//        boardEntity.setDeleteDate(dateFormat);
    }
}