package com.wekids.backend.member.repository;

import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.domain.Parent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Test
    void select() {
        List<Member> all = memberRepository.findAll();
        System.out.println("all = " + all);
    }

    @Test
    void findParentByChild() {
        Parent parent = memberRepository.findParentByChildId(2L).get();
        System.out.println("parent = " + parent);
        assertThat(parent.getId()).isEqualTo(1L);
    }


}