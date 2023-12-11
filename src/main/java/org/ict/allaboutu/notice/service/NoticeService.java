package org.ict.allaboutu.notice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.common.FileNameChange;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.ict.allaboutu.notice.domain.Notice;
import org.ict.allaboutu.notice.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
                    .originalFileName(notice.getOriginalFileName())
                    .renameFileName(notice.getRenameFileName())
                    .readCount(notice.getReadCount())
                    .build();
        });
        return  noticeDtoList;
    }

    public List<NoticeDto> getImportantNotice() {
        List<Notice> notices = noticeRepository.findImportantNotice();
        List<NoticeDto> noticeDtoList = notices.stream().map(notice -> {
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
                    .originalFileName(notice.getOriginalFileName())
                    .renameFileName(notice.getRenameFileName())
                    .readCount(notice.getReadCount())
                    .build();
        }).toList();

        return  noticeDtoList;
    }

    //검색처리
    public Page<NoticeDto> searchNoticesByKeyword( String searchType,String keyword, Pageable pageable) {
        
        Page<Notice> noticeList =   null;

         if(searchType.equals("title") ){
              noticeList =   noticeRepository.findByNoticeTitle(keyword, pageable);
         }else if(searchType.equals("contents")) {
             noticeList =   noticeRepository.findByNoticeContents(keyword, pageable);
        }
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
                    .originalFileName(notice.getOriginalFileName())
                    .renameFileName(notice.getRenameFileName())
                    .readCount(notice.getReadCount())
                    .build();
        });
        return  noticeDtoList;
    }

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
                .originalFileName(notice.getOriginalFileName())
                .renameFileName(notice.getRenameFileName())
                .readCount(notice.getReadCount()).build();

        return noticeDto;
    }
    public Notice noticeUp(@RequestBody Notice notice){
        return noticeRepository.save(notice);
    }

    @Transactional
    public NoticeDto getByNoticeId(Long noticeNum) {
        Notice notice = noticeRepository.findById(noticeNum).get();

        NoticeDto noticeDto = null;

        if (notice != null) {
            noticeRepository.updateReadCount(noticeNum);
            notice.setReadCount(notice.getReadCount() + 1);

            Member member = memberRepository.findById(notice.getUserNum()).get();

             noticeDto = NoticeDto.builder()
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
                     .originalFileName(notice.getOriginalFileName())
                     .renameFileName(notice.getRenameFileName())
                    .readCount(notice.getReadCount())
                    .writeDate(notice.getWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .build();

        }

        return noticeDto; // 또는 필요한 경우 사용자 정의 예외를 throw
    }

    public NoticeDto updateNotice(Notice notice, MultipartFile file) {
        Notice oldNotice = noticeRepository.findById(notice.getNoticeNum()).get();
        //파일 저장 전 기존 파일 삭제
       if(oldNotice.getRenameFileName() != null){
           String savePath = System.getProperty("user.dir") + "/src/main/resources/board_upload/";
           File deleteFile = new File(savePath, oldNotice.getRenameFileName());
           deleteFile.delete();
       }

        //파일 저장
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

        //Notice 테이블 저장
        notice.setUserNum(oldNotice.getUserNum());
        notice.setCartegory(oldNotice.getCartegory());
        notice.setWriteDate(oldNotice.getWriteDate());
        notice.setEventStart(oldNotice.getEventStart());
        notice.setEventEnd(oldNotice.getEventEnd());
        notice.setImportance(oldNotice.getImportance());
        notice.setImportanceDate(oldNotice.getImportanceDate());
        notice.setWriteDate(oldNotice.getWriteDate());
        notice.setModifyDate(LocalDateTime.now());
        notice.setReadCount(oldNotice.getReadCount());
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
                .originalFileName(notice.getOriginalFileName())
                .renameFileName(notice.getRenameFileName())
                .readCount(notice.getReadCount())
                .writeDate(notice.getWriteDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();

        return noticeDto;
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