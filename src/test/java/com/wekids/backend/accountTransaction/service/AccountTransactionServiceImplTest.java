package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.support.fixture.AccountFixture;
import com.wekids.backend.support.fixture.AccountTransactionFixture;
import com.wekids.backend.support.fixture.ChildFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountTransactionServiceImplTest {
    private static final Logger log = LoggerFactory.getLogger(AccountTransactionServiceImplTest.class);
    @Mock
    private AccountTransactionRepository accountTransactionRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountTransactionServiceImpl accountTransactionService;



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