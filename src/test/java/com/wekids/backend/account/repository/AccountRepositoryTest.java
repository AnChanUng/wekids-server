package com.wekids.backend.account.repository;

import com.wekids.backend.account.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Test
    void test() {
        List<Account> all = accountRepository.findAll();
        System.out.println("all = " + all);
    }
}