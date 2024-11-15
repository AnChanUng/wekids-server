package com.wekids.backend.accountTransaction.repository;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
class AccountTransactionRepositoryTest {
    @Autowired
    AccountTransactionRepository accountTransactionRepository;

    @Test
    void test() {
        List<AccountTransaction> all = accountTransactionRepository.findAll();
        System.out.println("all = " + all);

    }

}