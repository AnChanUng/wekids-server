package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.support.fixture.AccountFixture;
import com.wekids.backend.support.fixture.AccountTransactionFixture;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.support.fixture.ChildFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AccountTransactionServiceTest {

    @Mock private AccountTransactionRepository accountTransactionRepository;
    @Mock private AccountRepository accountRepository;
    @InjectMocks private AccountTransactionServiceImpl accountTransactionService;


    @Test
    void 거래_정보를_필터링하여_조회한다() {
        // Arrange
        String start = "2024-01-01T00:00:00";
        String end = "2024-12-31T23:00:00";
        int page = 0;
        int size = 5;

        AccountTransaction accountTransaction = new AccountTransactionFixture().build();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateStartTime = LocalDateTime.parse(start, formatter);
        LocalDateTime localDateEndTime = LocalDateTime.parse(end, formatter);
        Member member = ChildFixture.builder().id(3L).name("박민수").build();
        Account account = AccountFixture.builder().member(member).build();


        Pageable pageable = PageRequest.of(page, size);
        Slice<AccountTransaction> expectedSlice = new PageImpl<>(List.of(accountTransaction), pageable, pageable.getPageSize() + 1);
        given(accountRepository.findById(account.getId())).willReturn(Optional.of(account));
        given(accountTransactionRepository.findSliceBySenderOrReceiverAndCreatedAtBetweenAndType(
                pageable, "박민수", "박민수", localDateStartTime, localDateEndTime, accountTransaction.getType()))
                .willReturn(expectedSlice);

        TransactionListResponse response = accountTransactionService.showTransactionList(account.getId(), localDateStartTime, localDateEndTime, String.valueOf(accountTransaction.getType()), page, size);

        assertThat(response.getTransactions().size()).isEqualTo(1);
        assertThat(response.getBalance()).isEqualTo("50000.00");
    }

}
