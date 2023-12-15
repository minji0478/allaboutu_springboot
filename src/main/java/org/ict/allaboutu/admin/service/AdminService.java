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

    public List<AdminDto> getMemberList() {

        List<AdminDto> dtos = new ArrayList<>();
        List<Admin> adminEntites = adminRepository.findAll();

        for (Admin adminEntity : adminEntites) {
            AdminDto adminDto = AdminDto.builder()
                    .userNum(adminEntity.getUserNum())
                    .userId(adminEntity.getUserId())
                    .userEmail(adminEntity.getUserEmail())
                    .userPhone(adminEntity.getUserPhone())
                    .enrolleDate(adminEntity.getEnrollDate())
                    .account(adminEntity.getAccount())
                    .build();

            dtos.add(adminDto);
        }

        return dtos;
    }

    public List<AdminDto> getReportList() {
        List<AdminDto> dtos = new ArrayList<>();
        List<Report> reportEntites = reportRepository.findAll();

        for (Report reportEntity : reportEntites){
            AdminDto adminDto = AdminDto.builder()
                    .boardNum(reportEntity.getBoardNum())
                    .reportNum(reportEntity.getReportNum())
                    .reportCause(reportEntity.getReportCause())
                    .reportReason(reportEntity.getReportReason())
                    .deleteDate(reportEntity.getDeleteDate())
                    .build();

            dtos.add(adminDto);
        }

        return dtos;
    }

    public Board detailReportBoard(Long boardNum) throws Exception {
        Optional<Board> boardEntity = boardRepository.findById(boardNum);
        if(boardEntity.isPresent()){
            Board board = boardEntity.get();

            String boardTitle = board.getBoardTitle();
            String boardContent = board.getBoardContent();

            return board;

        }else{
            throw new EntityNotFoundException("Board not found with boardNum: " + boardNum);
        }
    }

    public Admin updateMemberAccount(Long userNum, AdminDto adminDto) throws Exception {

        Admin adminEntity = adminRepository.findById(userNum)
                .orElseThrow(() -> new EntityNotFoundException("서비스에는 Id로 찾은 값이 없다"));

        adminEntity.setAccount(adminDto.getAccount());

        return adminRepository.save(adminEntity);
    }

    public Long updateReportBoard(Long reportNum) throws Exception {
        Report reportEntity = reportRepository.findById(reportNum)
                .orElseThrow(() -> new EntityNotFoundException("Exception 처리함"));

        LocalDateTime date = LocalDateTime.now();
        reportEntity.setDeleteDate(date);

        reportRepository.save(reportEntity);


        return reportNum;
    }
}