package org.ict.allaboutu.member.service;

import lombok.RequiredArgsConstructor;
import org.ict.allaboutu.member.repository.MemberRepositoty;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepositoty repositoty;
}
