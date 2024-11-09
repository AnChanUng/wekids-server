package com.wekids.backend.member.repository;

import com.wekids.backend.account.domain.enums.AccountDesignType;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.dto.request.ParentAccountRequest;
import com.wekids.backend.member.dto.response.ParentAccountResponse;
import com.wekids.backend.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Test
    void select() {
        List<Member> all = memberRepository.findAll();
        System.out.println("all = " + all);
    }
    @Test
    void 부모_정보_가져오기(){
        ParentAccountRequest request = new ParentAccountRequest(1L);
        ParentAccountResponse response = memberService.getParent_Account(request);
        assertThat(response.getAccountNumber()).isEqualTo("123-456-7890");
        assertThat(response.getProfile()).isEqualTo("profile1.jpg");
        assertThat(response.getBalance()).isEqualByComparingTo("100000.00");
        assertThat(response.getDesignType()).isEqualTo(AccountDesignType.WEBEE);
        assertThat(response.getAccountId()).isEqualTo(1);
        assertThat(response.getChildren()).hasSize(2);
    }

}