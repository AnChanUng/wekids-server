package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.support.fixture.AccountFixture;
import com.wekids.backend.support.fixture.ChildFixture;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.BDDMockito.given;

class AccountTransactionServiceImplTest {
    private static final Logger log = LoggerFactory.getLogger(AccountTransactionServiceImplTest.class);
    @Mock
    private AccountTransactionRepository accountTransactionRepository;
    @InjectMocks
    private AccountTransactionService accountTransactionService;

    @Test
    void 계좌_상세뷰를_id로_조회한다() {
        Long accountId = 1L;
        Member member = ChildFixture.builder()
                .id(2L)
                .build();
        Account account = new AccountFixture().builder().member(member).build();

        given(accountTransactionRepository.findById(account.getId()));
        accountTransactionService.findByTransactionId(accountId);
    }

}