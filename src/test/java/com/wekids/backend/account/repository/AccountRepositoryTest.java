package com.wekids.backend.account.repository;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountDesignType;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.account.dto.response.AccountResponse;
import com.wekids.backend.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;

    @Test
    void test() {
        List<Account> all = accountRepository.findAll();
        System.out.println("all = " + all);
    }

    @Test
    void 계좌를_가져온다(){
        List<AccountResponse> accountResponseList = accountService.getAccount();
        assertThat(accountResponseList.size()).isEqualTo(3);

    }
}