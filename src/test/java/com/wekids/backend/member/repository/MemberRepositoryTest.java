package com.wekids.backend.member.repository;

import com.wekids.backend.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Test
    void select() {
        List<Member> all = memberRepository.findAll();
        System.out.println("all = " + all);
    }

}