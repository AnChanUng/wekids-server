package com.wekids.backend.account.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.dto.response.AccountChildResponse;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.repository.MemberRepository;
import com.wekids.backend.support.fixture.AccountFixture;
import com.wekids.backend.support.fixture.ChildFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void 자식_계좌_리스트를_조회한다(){
        long parentId = 1L;


        Child child1 = new ChildFixture().id(1L).build();
        Child child2 = new ChildFixture().id(2L).build();

        // List<Child> 생성 및 추가
        List<Child> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);
        Account account = AccountFixture.builder().member(child1).build();
        given(memberRepository.findChildrenByParentId(parentId)).willReturn(children);

        List<AccountChildResponse> childListResponseList = children.stream().map(child -> {
            // 각 자녀의 계좌 정보 조회
            given(accountRepository.findByMember(child)).willReturn(Optional.of(account));
            return AccountChildResponse.from(account);
        }).toList();

        assertThat(accountService.findChildrenAccountList(1L).get(0).getAccountId())
                .isEqualTo(childListResponseList.get(0).getAccountId());
        assertThat(accountService.findChildrenAccountList(1L).size())
                .isEqualTo(2);


    }
}
