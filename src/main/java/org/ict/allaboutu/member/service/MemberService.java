package org.ict.allaboutu.member.service;

import lombok.RequiredArgsConstructor;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.domain.UserRole;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "allaboutu.mailservice@gmail.com";

    public MemberDto signup(Member member) {

        member.setUserNum(memberRepository.findMaxUserNum() + 1L);
        member.setEnrollDate(LocalDateTime.now());
        member.setAdmin("N");
        member.setAccount("N");
        member.setReportCount(0L);
        member.setEnrollType("normal");
        member.setAccount("N");
        member.setRole(UserRole.USER);
        Member savedMember = memberRepository.save(member);

        return MemberDto.builder()
                .userNum(savedMember.getUserNum())
                .userId(savedMember.getUserId())
                .userName(savedMember.getUserName())
                .userPwd(savedMember.getUserPwd())
                .userEmail(savedMember.getUserEmail())
                .userGender(savedMember.getUserGender())
                .userPhone(savedMember.getUserPhone())
                .userProfile(savedMember.getUserProfile())
                .enrollType(savedMember.getEnrollType())
                .enrollDate(savedMember.getEnrollDate().toString())
                .admin(savedMember.getAdmin())
                .account(savedMember.getAccount())
                .reportCount(savedMember.getReportCount())
                .role(savedMember.getRole())
                .build();
    }

    public MemberDto getMember(String userId) {
        Member member = memberRepository.findByUserId(userId);

        return MemberDto.builder()
                .userNum(member.getUserNum())
                .userId(member.getUserId())
                .userName(member.getUserName())
                .userPwd(member.getUserPwd())
                .userEmail(member.getUserEmail())
                .userGender(member.getUserGender())
                .userPhone(member.getUserPhone())
                .userProfile(member.getUserProfile())
                .enrollType(member.getEnrollType())
                .enrollDate(member.getEnrollDate().toString())
                .admin(member.getAdmin())
                .account(member.getAccount())
                .reportCount(member.getReportCount())
                .role(member.getRole())
                .build();
    }

    public String getUserId(Long userNum) {
        return memberRepository.findById(userNum).get().getUserId();
    }

    public String findIdByUserEmail(String userEmail){
        Member member = memberRepository.findByUserEmail(userEmail);
        return member != null ? member.getUserId() : null;
    }

    public boolean checkUserInfo(String userId, String userEmail) {
        Member member = memberRepository.findByUserId(userId);
        return member != null && member.getUserEmail().equals(userEmail);
    }

}
