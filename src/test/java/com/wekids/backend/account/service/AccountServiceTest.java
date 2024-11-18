package com.wekids.backend.account.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.dto.response.AccountResponse;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.support.fixture.AccountFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {


    @Mock private AccountRepository accountRepository;
    @InjectMocks
    private AccountServiceImpl accountService;


    @Test
    void 계좌를_가져온다(){

        Account account1 = new AccountFixture().build();
        Account account2 = new AccountFixture().build();

        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);

        given(accountRepository.findAll()).willReturn(accounts);

        List<AccountResponse> accountResponseList = accountService.getAccount();
        assertThat(accountResponseList.size()).isEqualTo(2);

    }
}
