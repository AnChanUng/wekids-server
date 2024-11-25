package com.wekids.backend.member.repository;

import com.wekids.backend.member.domain.Child;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ChildRepositoryTest {

    @Autowired
    private ChildRepository childRepository;

    @Test
    void Child_ID로_조회시_값이_없으면_OPTIONAL_EMPTY_반환() {

        Long nonExistentId = 999L;

        Optional<Child> result = childRepository.findById(nonExistentId);

        assertThat(result).isEmpty();
    }

    @Test
    void Child_ID로_조회시_값이있으면_optional에_엔티티_반환() {

        Child child = Child.builder()
                .id(1L)
                .name("안찬웅")
                .phone("01085443140")
                .birthday(LocalDate.of(2012, 1, 1))
                .profile("hhh.jpg")
                .email("1stevering@naver.com")
                .simplePassword("1234")
                .build();
        childRepository.save(child);

        Optional<Child> result = childRepository.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getName()).isEqualTo("안찬웅");
        assertThat(result.get().getPhone()).isEqualTo("01085443140");
        assertThat(result.get().getBirthday()).isEqualTo(LocalDate.of(2012, 1, 1));
        assertThat(result.get().getProfile()).isEqualTo("hhh.jpg");
        assertThat(result.get().getEmail()).isEqualTo("1stevering@naver.com");
        assertThat(result.get().getSimplePassword()).isEqualTo("1234");
    }
}
