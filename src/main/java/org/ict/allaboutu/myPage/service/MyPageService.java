package org.ict.allaboutu.myPage.service;

import jakarta.transaction.Transactional;
import org.apache.groovy.util.BeanUtils;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.ict.allaboutu.myPage.repository.MyPageRepository;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {
    private final MyPageRepository myPageRepository;

    public MyPageService(MyPageRepository myPageRepository){
        this.myPageRepository = myPageRepository;
    }

    private MemberRepository memberRepository;

    public MyPageDto getMyPage(Long userId){
        Member member = memberRepository.findById(userId).get();
        if (member == null){
            return null;
        }
        MyPageDto myPageDto = new MyPageDto();
        myPageDto.setUserNum(member.getUserNum());
        myPageDto.setUserName(member.getUserName());
        myPageDto.setUserPwd(member.getUserPwd());
        myPageDto.setUserEmail(member.getUserEmail());
        myPageDto.setUserPhone(member.getUserPhone());
        return myPageDto;

    }

//    @Transactional
//    public MyPageDto updateUser(Long userId, String userPhone, String userPwd) {
//        Member member = myPageRepository.findById(userId).get();
//        if(member != null){
//            member.setUserPhone(userPhone);
//            member.setUserPwd(userPwd);
//            myPageRepository.save(member);
//        }
//        return myPageDto;
//    }

}
