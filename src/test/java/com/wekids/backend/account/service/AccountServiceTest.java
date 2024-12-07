package com.wekids.backend.account.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.dto.response.AccountChildResponse;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.support.fixture.AccountFixture;
import com.wekids.backend.support.fixture.ChildFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock private AccountRepository accountRepository;
    @InjectMocks private AccountServiceImpl accountService;

    @Test
    void 자식_계좌_리스트를_조회한다() {
        // Given
        Long parentId = 1L;
        Child child1 = ChildFixture.builder().id(1L).build().child();
        Child child2 = ChildFixture.builder().id(2L).build().child();

        Account account1 = AccountFixture.builder().id(1L).member(child1).build().account();
        Account account2 = AccountFixture.builder().id(2L).member(child2).build().account();

        List<Account> accounts = List.of(account1, account2);

        given(accountRepository.findAccountsByParentId(parentId)).willReturn(accounts);

        // When
        List<AccountChildResponse> responses = accountService.showChildrenAccountList(parentId);

        // Then
        assertThat(responses).hasSize(2);

        AccountChildResponse response1 = responses.get(0);
        assertThat(response1.getName()).isEqualTo(child1.getName());
        assertThat(response1.getAccountNumber()).isEqualTo(account1.getAccountNumber());
        assertThat(response1.getProfile()).isEqualTo(child1.getProfile());
        assertThat(response1.getAccountId()).isEqualTo(account1.getId());

        AccountChildResponse response2 = responses.get(1);
        assertThat(response2.getName()).isEqualTo(child2.getName());
        assertThat(response2.getAccountNumber()).isEqualTo(account2.getAccountNumber());
        assertThat(response2.getProfile()).isEqualTo(child2.getProfile());
        assertThat(response2.getAccountId()).isEqualTo(account2.getId());
    }

    @Test
    void 부모_ID에_해당하는_계좌가_없는_경우() {
        // Given
        Long parentId = 1L;

        given(accountRepository.findAccountsByParentId(parentId)).willReturn(List.of());

        // When
        List<AccountChildResponse> responses = accountService.showChildrenAccountList(parentId);

        // Then
        assertThat(responses).isEmpty();
    }

}
