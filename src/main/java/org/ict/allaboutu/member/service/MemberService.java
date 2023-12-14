package org.ict.allaboutu.member.service;

import lombok.RequiredArgsConstructor;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.domain.UserRole;
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
        if (member == null || "Y".equals(member.getAccount())) {
            // 사용자를 찾지 못하거나 계정이 'Y'인 경우 null을 반환하여 로그인이 허용되지 않도록 함
            return null;
        }
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
}
