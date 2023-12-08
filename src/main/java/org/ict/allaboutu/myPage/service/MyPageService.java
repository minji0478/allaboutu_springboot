package org.ict.allaboutu.myPage.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.ict.allaboutu.myPage.repository.MyPageRepository;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyPageService {

    private final MemberRepository memberRepository;

    public MyPageDto getMyPage(Long userNum){
        Member member = memberRepository.findById(userNum).get();
        if (member == null){
            return null;
        }
        MyPageDto myPageDto = MyPageDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .userPwd(member.getUserPwd())
                .userEmail(member.getUserEmail())
                .userPhone(member.getUserPhone())
                .build();
        return myPageDto;

    }

    @Transactional
    public MyPageDto updateUser(Long userNum, Member updateMember) {
        Member member = memberRepository.findById(userNum).get();

        if (member == null){
            return null;
        }
        MyPageDto myPageDto = MyPageDto.builder()
                .userPwd(member.getUserPwd())
                .userPhone(member.getUserPhone())
                .build();
        return myPageDto;
    }

}
