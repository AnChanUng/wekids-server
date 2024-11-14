package com.wekids.backend.accountTransaction.repository;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;
import com.wekids.backend.accountTransaction.dto.result.TransactionListResult;
import com.wekids.backend.accountTransaction.service.AccountTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountTransactionRepositoryTest {
    @Autowired
    AccountTransactionRepository accountTransactionRepository;

    @Autowired
    AccountTransactionService accountTransactionService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    void test() {
        List<AccountTransaction> all = accountTransactionRepository.findAll();
        System.out.println("all = " + all);

    }

    @Test
    void 입력값_테스트(){

        TransactionListResponse response = accountTransactionService.getTransactionList(1, "2024-01-01T00:00:00", "2024-12-31T23:00:00", "WITHDRAWAL", 1, 5);
        assertThat(response.getBalance()).isEqualTo("100000.00");
        assertThat(response.isHasNext()).isTrue();
        assertThat(response.getTransactions().size()).isEqualTo(5);
    }

}