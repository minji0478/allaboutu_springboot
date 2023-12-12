package org.ict.allaboutu.member.service;

import lombok.RequiredArgsConstructor;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDto signup(Member member) {

        member.setUserNum(memberRepository.findMaxUserNum() + 1L);
        member.setEnrollDate(LocalDateTime.now());
        member.setAdmin("N");
        member.setAccount("N");
        member.setReportCount(0L);
        member.setEnrollType("normal");
        member.setAccount("N");
        Member savedMember = memberRepository.save(member);

        return MemberDto.builder()
                .userNum(savedMember.getUserNum())
                .userId(savedMember.getUserId())
                .userName(savedMember.getUsername())
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
                .build();
    }

    public MemberDto getMember(String userId) {
        Member member = memberRepository.findByUserId(userId);
        if (member == null) {
            return null;
        }
        return MemberDto.builder()
                .userNum(member.getUserNum())
                .userId(member.getUserId())
                .userName(member.getUsername())
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
                .build();
    }

    public String getUserId(Long userNum) {
        return memberRepository.findById(userNum).get().getUserId();
    }
}
