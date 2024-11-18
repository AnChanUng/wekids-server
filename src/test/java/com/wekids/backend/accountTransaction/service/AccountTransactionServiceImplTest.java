package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.support.fixture.AccountFixture;
import com.wekids.backend.support.fixture.AccountTransactionFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @InjectMocks
    private AccountTransactionServiceImpl accountTransactionService;

    @Test
    void 거래_상세뷰를_id로_조회한다() {
        Long transactionId = 1L;
        Account account = AccountFixture.builder()
                .id(1L)
                .accountNumber("123-456-789")
                .balance(BigDecimal.valueOf(250.00))
                .state(AccountState.ACTIVE)
                .build();
        System.out.println(account);
        // AccountTransactionFixture를 사용하여 거래 객체를 생성합니다.
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
                .account(account)
                .build();

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

}
