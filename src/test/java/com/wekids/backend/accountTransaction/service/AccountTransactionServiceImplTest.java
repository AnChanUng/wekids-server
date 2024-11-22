package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import com.wekids.backend.accountTransaction.dto.request.UpdateMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.support.fixture.AccountFixture;
import com.wekids.backend.support.fixture.AccountTransactionFixture;
import com.wekids.backend.support.fixture.ChildFixture;
import org.junit.jupiter.api.BeforeEach;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountTransactionServiceImplTest {
    @Mock
    private AccountTransactionRepository accountTransactionRepository;
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountTransactionServiceImpl accountTransactionService;

    private Account account;
    private AccountTransaction transaction;

    @BeforeEach
    void setUp() {
        account = createAccount();
        transaction = createAccountTransaction(account);
    }

    private Account createAccount() {
        return AccountFixture.builder()
                .id(1L)
                .accountNumber("123-456-789")
                .balance(BigDecimal.valueOf(250.00))
                .state(AccountState.ACTIVE)
                .build();
    }

    private AccountTransaction createAccountTransaction(Account account) {
        return AccountTransactionFixture.builder().build(account);
    }

    @Test
    void 거래_상세뷰를_id로_조회한다() {
        Long transactionId = 1L;

        AccountTransaction transaction = AccountTransactionFixture.builder().build(account);

        // Mocking the repository to return the transaction when queried by ID
        given(accountTransactionRepository.findById(transactionId)).willReturn(Optional.of(transaction));

        // Call the service method to retrieve the transaction details
        TransactionDetailSearchResponse response = accountTransactionService.findByTransactionId(transactionId);

        // Assertions to verify the response
        assertAll(
                () -> verify(accountTransactionRepository, times(1)).findById(transactionId),
                () -> assertThat(response.getTitle()).isEqualTo(transaction.getTitle()),
                () -> assertThat(response.getType()).isEqualTo(transaction.getType()),
                () -> assertThat(response.getAmount()).isEqualTo(transaction.getAmount()),
                () -> assertThat(response.getBalance()).isEqualTo(transaction.getBalance()),
                () -> assertThat(response.getMemo()).isEqualTo(transaction.getMemo())

        );
    }

    @Test
    void 메모를_업데이트한다() {
        Long transactionId = 1L;
        // AccountTransactionFixture를 사용하여 거래 객체를 생성
        AccountTransaction transaction = AccountTransactionFixture.builder()
                .id(transactionId)
                .title("카카오페이")
                .type(TransactionType.DEPOSIT)
                .amount(BigDecimal.valueOf(100.00))
                .balance(BigDecimal.valueOf(1000.00))
                .sender("Sender Name")
                .receiver("Receiver Name")
                .memo("")
                .createdAt(LocalDateTime.now())
                .build(account);

        given(accountTransactionRepository.findById(transactionId)).willReturn(Optional.of(transaction));

        String newMemo = "메모 업데이트";
        UpdateMemoRequest request = new UpdateMemoRequest(newMemo);

        accountTransactionService.saveMemo(transactionId, request);

        // 메모가 업데이트되었는지 확인
        assertThat(transaction.getMemo()).isEqualTo(newMemo);

        // 메모를 저장하는 메서드가 호출되었는지 확인
        verify(accountTransactionRepository, times(1)).save(transaction);
    }

}