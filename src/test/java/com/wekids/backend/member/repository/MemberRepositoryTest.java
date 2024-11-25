package com.wekids.backend.member.repository;

import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    ParentRepository parentRepository;
    @Autowired
    ChildRepository childRepository;

    @Test
    void findParentByChild() {
        Parent parent = parentRepository.findParentByChildId(2L).get();
        System.out.println("parent = " + parent);
        assertThat(parent.getId()).isEqualTo(1L);
    }

    @Test
    void findChildById() {
        Child child = childRepository.findById(2L).get();
        System.out.println("child = " + child);
        assertThat(child.getName()).isEqualTo("구자빈");
    }

}