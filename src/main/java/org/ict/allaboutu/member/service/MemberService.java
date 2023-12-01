package org.ict.allaboutu.member.service;

import lombok.RequiredArgsConstructor;
import org.ict.allaboutu.member.domain.Member;
import org.ict.allaboutu.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository repository;
}
