package org.ict.allaboutu.admin.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.admin.domain.Admin;
import org.ict.allaboutu.admin.domain.Report;
import org.ict.allaboutu.admin.repository.AdminRepository;
import org.ict.allaboutu.admin.repository.ReportRepository;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final ReportRepository reportRepository;
    private final BoardRepository boardRepository;

    public Page<AdminDto> getMemberList(Pageable pageable) {
        List<AdminDto> dtos = new ArrayList<>();
        Page<Admin> adminEntites = adminRepository.findAll(pageable);
        Page<Report> reportEntites = reportRepository.findAll(pageable);

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
                    .deleteDate(reportEntity.getDeleteDate())
                    .build();
        });

        return adminDtos;
    }

    public Long updateReportBoard(Long boardNum) throws Exception {
        Optional<Board> optionalBoard = boardRepository.findById(boardNum);

        if (optionalBoard.isPresent()) {
            Board boardEntity = optionalBoard.get();

            log.info("boardEntity: " + boardEntity);

            LocalDateTime dateFormat = LocalDateTime.now();
            boardEntity.setDeleteDate(dateFormat);

            boardRepository.save(boardEntity);
            Optional<Report> reportOptional = reportRepository.findByReportNum(boardNum);
            if (reportOptional.isPresent()){
                Report reportEntity = reportOptional.get();

                reportEntity.setDeleteDate(dateFormat);
                reportRepository.save(reportEntity);
            }else{
                throw new EntityNotFoundException("report not found with boardNum: " + boardNum);
            }

        } else {
            throw new EntityNotFoundException("Board not found with boardNum: " + boardNum);
        }
        return boardNum;
    }
}