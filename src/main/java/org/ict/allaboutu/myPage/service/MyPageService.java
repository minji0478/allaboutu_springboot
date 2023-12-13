package org.ict.allaboutu.myPage.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.ict.allaboutu.member.service.MemberDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private final MemberRepository memberRepository;


    @Transactional
    public MemberDto updateUser(String userId, Member updateMember) {
        Member member = memberRepository.findByUserId(userId);

        if (member == null){
            return null;

        }
        if(updateMember.getUserPhone() != null){
            member.setUserPhone(updateMember.getUserPhone());
        }

        if(updateMember.getUserPwd() != null) {
            String hashedPassword = new BCryptPasswordEncoder().encode(updateMember.getUserPwd());
            member.setUserPwd(hashedPassword);
        }

        memberRepository.save(member);

        MemberDto memberDto = MemberDto.builder()
                .userNum(member.getUserNum())
                .userId(member.getUserId())
                .userName(member.getUserName())
                .userEmail(member.getUserEmail())
                .userPhone(member.getUserPhone())
                .build();
        return memberDto;
    }

}
