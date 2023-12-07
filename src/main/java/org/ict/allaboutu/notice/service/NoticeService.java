package org.ict.allaboutu.notice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.board.domain.Board;
import org.ict.allaboutu.board.repository.BoardRepository;
import org.ict.allaboutu.board.repository.CommentRepository;
import org.ict.allaboutu.board.service.BoardDto;
import org.ict.allaboutu.common.FileNameChange;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.ict.allaboutu.notice.domain.Notice;
import org.ict.allaboutu.notice.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository  noticeRepository;
    private final MemberRepository memberRepository;

//    public List<Notice> getNoticeList() {
//            List<Notice> noticeList = noticeRepository.findAll(Sort.by(Sort.Direction.DESC,"noticeNum"));
//
//        return noticeList;
//    }

    public Page<NoticeDto> getNoticeList(Pageable pageable) {

        Page<Notice> noticeList = noticeRepository.findAll(pageable);
        Page<NoticeDto> noticeDtoList = noticeList.map(notice -> {
            Member member = memberRepository.findById(notice.getUserNum()).get();

            return NoticeDto.builder()
                    .noticeNum(notice.getNoticeNum())
                    .userNum(member.getUserNum())
                    .userName(member.getUserName())
                    .noticeTitle(notice.getNoticeTitle())
                    .noticeContents(notice.getNoticeContents())
                    .cartegory(notice.getCartegory())
                    .eventStart(notice.getEventStart() !=null ? notice.getEventStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "N/A")
                    .eventEnd(notice.getEventEnd()!=null ? notice.getEventEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "N/A")
                    .importance(notice.getImportance())
                    .importanceDate(notice.getImportanceDate()!=null ? notice.getImportanceDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "N/A")
                    .writeDate(notice.getWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .modifyDate(notice.getModifyDate() !=null ? notice.getModifyDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "N/A")
                    .OriginalFileName(notice.getOriginalFileName())
                    .RenameFileName(notice.getRenameFileName())
                    .ReadCount(notice.getReadCount())
                    .build();
        });
        return  noticeDtoList;


    }

//    public List<Notice> searchByTitle(String title) {
//        return noticeRepository.(title);
//    }

    public NoticeDto createNotice(Notice notice, MultipartFile file) {

        Long noticeNum = noticeRepository.findMaxNoticeNumber() + 1;
        notice.setNoticeNum(noticeNum);
        notice.setWriteDate(LocalDate.now());
        notice.setReadCount(0L);

        if(file != null){
            String renameFileName = null;
            try{
                renameFileName = FileNameChange.change(file.getOriginalFilename(), "yyyyMMddHHmmss");
                String savePath = System.getProperty("user.dir") + "/src/main/resources/notice_upload/";
                File saveFile = new File(savePath, renameFileName);
                file.transferTo(saveFile);
                notice.setOriginalFileName(file.getOriginalFilename());
                notice.setRenameFileName(renameFileName);
            }catch(Exception e) {
                e.printStackTrace();
            }

        }
        Notice saveNotice = noticeRepository.save(notice);

        Member member = memberRepository.findById(notice.getUserNum()).get();
        NoticeDto noticeDto = NoticeDto.builder()
                .noticeNum(notice.getNoticeNum())
                .userNum(member.getUserNum())
                .userName(member.getUserName())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContents(notice.getNoticeContents())
                .cartegory(notice.getCartegory())
                .eventStart(notice.getEventStart() !=null ? notice.getEventStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "N/A")
                .eventEnd(notice.getEventEnd()!=null ? notice.getEventEnd().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "N/A")
                .importance(notice.getImportance())
                .importanceDate(notice.getImportanceDate()!=null ? notice.getImportanceDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "N/A")
                .writeDate(notice.getWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .modifyDate(notice.getModifyDate() !=null ? notice.getModifyDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "N/A")
                .OriginalFileName(notice.getOriginalFileName())
                .RenameFileName(notice.getRenameFileName())
                .ReadCount(notice.getReadCount()).build();

        return noticeDto;
    }
    public Notice noticeUp(@RequestBody Notice notice){
        return noticeRepository.save(notice);
    }

    public Notice getByNoticeId(Long noticeNum) {
        Notice notice = noticeRepository.findById(noticeNum).get();
        return notice; // 또는 필요한 경우 사용자 정의 예외를 throw
    }

    public Notice updateNotice(Long noticeNum, Notice updatedNotice) {
            // 확인: 해당 noticeNum의 공지사항이 존재하는지 확인
            Notice notice = noticeRepository.findById(noticeNum).orElse(null);
        if (notice == null) {
            // 존재하지 않으면 null 반환 또는 예외 처리
            return null;
        }

        // 필요한 필드를 업데이트
        notice.setNoticeTitle(updatedNotice.getNoticeTitle());
        notice.setNoticeContents(updatedNotice.getNoticeContents());
        // 업데이트하고자 하는 다른 필드 추가

        // 업데이트된 공지사항 저장
        return noticeRepository.save(notice);
    }


    public NoticeDto getNoticeById(Long noticeNum) {

        Optional<Notice> optionalNotice = noticeRepository.findById(noticeNum);

        if (optionalNotice.isPresent()) {

            Notice notice = optionalNotice.get();
            notice.setReadCount(notice.getReadCount() + 1);
            noticeRepository.save(notice);

            Member member = memberRepository.findById(notice.getUserNum()).get();
            return NoticeDto.builder()
                    .noticeNum(notice.getNoticeNum())
                    .userNum(member.getUserNum())
                    .userName(member.getUserName())
                    .ReadCount(notice.getReadCount())
                    .build();
        } else {

            return null;
        }
    }

    public void deleteNotice(Long noticeNum) {
            // 확인: 해당 noticeNum의 공지사항이 존재하는지 확인
        Notice notice = noticeRepository.findById(noticeNum).orElse(null);
        if (notice != null) {
            // 존재하면 삭제
            noticeRepository.delete(notice);
        }
        // 존재하지 않아도 아무런 동작을 하지 않음 (에러를 발생시키거나 예외를 던지는 등의 처리도 가능)
    }





}