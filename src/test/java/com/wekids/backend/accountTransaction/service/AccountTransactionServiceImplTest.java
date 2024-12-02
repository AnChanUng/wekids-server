package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import com.wekids.backend.baas.dto.request.TransferRequest;
import com.wekids.backend.accountTransaction.dto.request.TransactionRequest;
import com.wekids.backend.accountTransaction.dto.request.UpdateMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.support.fixture.AccountFixture;
import com.wekids.backend.support.fixture.AccountTransactionFixture;
import com.wekids.backend.support.fixture.ChildFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountTransactionServiceImplTest {
    @Mock private AccountTransactionRepository accountTransactionRepository;
    @Mock private AccountRepository accountRepository;
    @Mock private RestTemplate restTemplate;
    @InjectMocks
    private AccountTransactionServiceImpl accountTransactionService;

    @Value("${baas.api.baas-url}")
    private String baasURL;

    private Member childMember;
    private Member parentMember;

    private Account account;
    private AccountTransaction transaction;

    @BeforeEach
    void setUp() {
        childMember = ChildFixture.builder().build().child();
        parentMember = ChildFixture.builder().name("강현우").email("5678").build().child();
        account = AccountFixture.builder()
                .id(1L)
                .accountNumber("123-456-789")
                .balance(BigDecimal.valueOf(250.00))
                .state(AccountState.ACTIVE)
                .build()
                .account();
    }

    private AccountTransaction createAccountTransaction(Account account) {
        return AccountTransactionFixture.builder().account(account).build().accountTransaction();
    }

    @Test
    void 거래_상세뷰를_id로_조회한다() {
        Long transactionId = 1L;

        AccountTransaction transaction = AccountTransactionFixture.builder().account(account).build().accountTransaction();

        given(accountTransactionRepository.findById(transactionId)).willReturn(Optional.of(transaction));

        TransactionDetailSearchResponse response = accountTransactionService.showTransaction(transactionId);

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
                .build()
                .accountTransaction();

        given(accountTransactionRepository.findById(transactionId)).willReturn(Optional.of(transaction));

        String newMemo = "메모 업데이트";
        UpdateMemoRequest request = new UpdateMemoRequest(newMemo);

        accountTransactionService.updateMemo(transactionId, request);

        // 메모가 업데이트되었는지 확인
        assertThat(transaction.getMemo()).isEqualTo(newMemo);
    }

    @Test
    void 이체금액을_제대로_부모_자식의_계좌에_업데이트_한다() {
        // Given
        BigDecimal transactionAmount = BigDecimal.valueOf(100.00);  //이체 금액

        Account childAccount = AccountFixture.builder()
                                    .accountNumber("CHILD1234567890")
                                    .balance(BigDecimal.valueOf(250.00))
                                    .state(AccountState.ACTIVE)
                                    .member(childMember)
                                    .build()
                                    .account();

        Account parentAccount = AccountFixture.builder()
                .accountNumber("PARENT1234567890")
                .balance(BigDecimal.valueOf(500.00))
                .state(AccountState.ACTIVE)
                .member(parentMember)
                .build()
                .account();

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .parentAccountNumber(parentAccount.getAccountNumber())
                .childAccountNumber(childAccount.getAccountNumber())
                .amount(transactionAmount)
                .sender(parentMember.getName())
                .receiver(childMember.getName())
                .build();

        when(accountRepository.findAccountByAccountNumber(parentAccount.getAccountNumber())).thenReturn(Optional.of(parentAccount));
        when(accountRepository.findAccountByAccountNumber(childAccount.getAccountNumber())).thenReturn(Optional.of(childAccount));

        // When
        accountTransactionService.transfer(transactionRequest);

        // Then
        ArgumentCaptor<AccountTransaction> transactionCaptor = ArgumentCaptor.forClass(AccountTransaction.class);
        verify(accountTransactionRepository, times(2)).save(transactionCaptor.capture());

        AccountTransaction parentTransaction = transactionCaptor.getAllValues().get(0);
        AccountTransaction childTransaction = transactionCaptor.getAllValues().get(1);

        assertThat(parentTransaction.getType()).isEqualTo(TransactionType.WITHDRAWAL);
        assertThat(parentTransaction.getAmount()).isEqualTo(transactionAmount);
        assertThat(parentTransaction.getBalance()).isEqualTo(BigDecimal.valueOf(400.00)); // 500 - 100

        assertThat(childTransaction.getType()).isEqualTo(TransactionType.DEPOSIT);
        assertThat(childTransaction.getAmount()).isEqualTo(transactionAmount);
        assertThat(childTransaction.getBalance()).isEqualTo(BigDecimal.valueOf(350.00)); // 200 + 100
    }

    @Test
    void 거래금액이_0이하일_때_예외가_발생한다() {
        // Given
        BigDecimal transactionAmount = BigDecimal.valueOf(0);

        Account childAccount = AccountFixture.builder()
                .accountNumber("CHILD12345")
                .balance(BigDecimal.valueOf(250.00))
                .state(AccountState.ACTIVE)
                .member(childMember)
                .build()
                .account();

        Account parentAccount = AccountFixture.builder()
                .accountNumber("PARENT12345")
                .balance(BigDecimal.valueOf(500.00))
                .state(AccountState.ACTIVE)
                .member(parentMember)
                .build()
                .account();

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .parentAccountNumber(parentAccount.getAccountNumber())
                .childAccountNumber(childAccount.getAccountNumber())
                .amount(transactionAmount)
                .sender(parentMember.getName())
                .receiver(childMember.getName())
                .build();

        when(accountRepository.findAccountByAccountNumber(parentAccount.getAccountNumber())).thenReturn(Optional.of(parentAccount));
        when(accountRepository.findAccountByAccountNumber(childAccount.getAccountNumber())).thenReturn(Optional.of(childAccount));

        // When & Then
        WekidsException exception = assertThrows(WekidsException.class, () ->
                accountTransactionService.transfer(transactionRequest)
        );

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_TRANSACTION_AMOUNT);
        assertThat(exception.getMessage()).contains("거래하려는 금액");
    }

    @Test
    void 부모계좌_잔액이_부족할_때_예외가_발생한다() {
        // Given
        BigDecimal transactionAmount = BigDecimal.valueOf(600.00);

        Account childAccount = AccountFixture.builder()
                .accountNumber("CHILD12345")
                .balance(BigDecimal.valueOf(250.00))
                .state(AccountState.ACTIVE)
                .member(childMember)
                .build()
                .account();

        Account parentAccount = AccountFixture.builder()
                .accountNumber("PARENT12345")
                .balance(BigDecimal.valueOf(500.00))  // 부족한 잔액
                .state(AccountState.ACTIVE)
                .member(parentMember)
                .build()
                .account();

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .parentAccountNumber(parentAccount.getAccountNumber())
                .childAccountNumber(childAccount.getAccountNumber())
                .amount(transactionAmount)
                .sender(parentMember.getName())
                .receiver(childMember.getName())
                .build();

        when(accountRepository.findAccountByAccountNumber(parentAccount.getAccountNumber())).thenReturn(Optional.of(parentAccount));
        when(accountRepository.findAccountByAccountNumber(childAccount.getAccountNumber())).thenReturn(Optional.of(childAccount));

        // When & Then
        WekidsException exception = assertThrows(WekidsException.class, () ->
                accountTransactionService.transfer(transactionRequest)
        );

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_TRANSACTION_AMOUNT);
        assertThat(exception.getMessage()).contains("부모의 잔액");
    }

    @Test
    void 계좌가_비활성화되어_있을_때_예외가_발생한다() {
        // Given
        BigDecimal transactionAmount = BigDecimal.valueOf(100.00);

        Account childAccount = AccountFixture.builder()
                .accountNumber("CHILD12345")
                .balance(BigDecimal.valueOf(250.00))
                .state(AccountState.INACTIVE)  // 비활성화된 계좌
                .member(childMember)
                .build()
                .account();

        Account parentAccount = AccountFixture.builder()
                .accountNumber("PARENT12345")
                .balance(BigDecimal.valueOf(500.00))
                .state(AccountState.ACTIVE)
                .member(parentMember)
                .build()
                .account();

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .parentAccountNumber(parentAccount.getAccountNumber())
                .childAccountNumber(childAccount.getAccountNumber())
                .amount(transactionAmount)
                .sender(parentMember.getName())
                .receiver(childMember.getName())
                .build();

        when(accountRepository.findAccountByAccountNumber(parentAccount.getAccountNumber())).thenReturn(Optional.of(parentAccount));
        when(accountRepository.findAccountByAccountNumber(childAccount.getAccountNumber())).thenReturn(Optional.of(childAccount));

        // When & Then
        WekidsException exception = assertThrows(WekidsException.class, () ->
                accountTransactionService.transfer(transactionRequest)
        );

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.ACCOUNT_NOT_ACTIVE);
        assertThat(exception.getMessage()).contains("자식 계좌 상태가 활성 상태가 아닙니다.");
    }

    @Test
    void 부모계좌와_자식계좌가_동일할_때_예외가_발생한다() {
        // Given
        String accountNumber = "PARENT12345";  // 동일한 계좌 번호
        BigDecimal transactionAmount = BigDecimal.valueOf(100.00);

        Account childAccount =  AccountFixture.builder()
                .accountNumber(accountNumber)  // 동일한 계좌 번호
                .balance(BigDecimal.valueOf(250.00))
                .state(AccountState.ACTIVE)
                .member(childMember)
                .build()
                .account();

        Account parentAccount = AccountFixture.builder()
                .accountNumber(accountNumber)  // 동일한 계좌 번호
                .balance(BigDecimal.valueOf(500.00))
                .state(AccountState.ACTIVE)
                .member(parentMember)
                .build()
                .account();

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .parentAccountNumber(accountNumber)
                .childAccountNumber(accountNumber)  // 동일한 계좌 번호
                .amount(transactionAmount)
                .sender(parentMember.getName())
                .receiver(childMember.getName())
                .build();

        when(accountRepository.findAccountByAccountNumber(accountNumber)).thenReturn(Optional.of(parentAccount));
        when(accountRepository.findAccountByAccountNumber(accountNumber)).thenReturn(Optional.of(childAccount));

        // When & Then
        WekidsException exception = assertThrows(WekidsException.class, () ->
                accountTransactionService.transfer(transactionRequest)
        );

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_ACCOUNT_NUMBER);
        assertThat(exception.getMessage()).contains("부모 계좌와 자식 계좌가 동일할 수 없습니다.");
    }

    @Test
    void 이체가_성공적으로_업데이트_된_경우() {
        BigDecimal transactionAmount = BigDecimal.valueOf(100.00);  //이체 금액

        Account childAccount = AccountFixture.builder()
                .accountNumber("CHILD1234567890")
                .balance(BigDecimal.valueOf(250.00))
                .state(AccountState.ACTIVE)
                .member(childMember)
                .build()
                .account();

        Account parentAccount = AccountFixture.builder()
                .accountNumber("PARENT1234567890")
                .balance(BigDecimal.valueOf(500.00))
                .state(AccountState.ACTIVE)
                .member(parentMember)
                .build()
                .account();

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .parentAccountNumber(parentAccount.getAccountNumber())
                .childAccountNumber(childAccount.getAccountNumber())
                .amount(transactionAmount)
                .sender(parentMember.getName())
                .receiver(childMember.getName())
                .build();

        when(accountRepository.findAccountByAccountNumber("PARENT1234567890")).thenReturn(Optional.of(parentAccount));
        when(accountRepository.findAccountByAccountNumber("CHILD1234567890")).thenReturn(Optional.of(childAccount));

        accountTransactionService.transfer(transactionRequest);

        assertEquals(new BigDecimal("350.0"), childAccount.getBalance()); // 자식 계좌 잔액 확인
        assertEquals(new BigDecimal("400.0"), parentAccount.getBalance());

        verify(accountTransactionRepository, times(2)).save(any(AccountTransaction.class)); // 트랜잭션 저장 확인
        verify(accountRepository, never()).save(any(Account.class)); // Account는 변경 감지로 저장됨
    }

    @Test
    void BaaS_이체요청_성공() {
        // Given
        BigDecimal transactionAmount = BigDecimal.valueOf(100);

        Account parentAccount = AccountFixture.builder()
                .accountNumber("PARENT123456")
                .balance(BigDecimal.valueOf(500))
                .state(AccountState.ACTIVE)
                .member(parentMember)
                .build()
                .account();

        Account childAccount = AccountFixture.builder()
                .accountNumber("CHILD123456")
                .balance(BigDecimal.valueOf(200))
                .state(AccountState.ACTIVE)
                .member(childMember)
                .build()
                .account();

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .parentAccountNumber(parentAccount.getAccountNumber())
                .childAccountNumber(childAccount.getAccountNumber())
                .amount(transactionAmount)
                .sender(parentMember.getName())
                .receiver(childMember.getName())
                .build();

        when(accountRepository.findAccountByAccountNumber(parentAccount.getAccountNumber())).thenReturn(Optional.of(parentAccount));
        when(accountRepository.findAccountByAccountNumber(childAccount.getAccountNumber())).thenReturn(Optional.of(childAccount));
        when(restTemplate.postForLocation(eq(baasURL + "/api/v1/transactions"), any(TransferRequest.class)))
                .thenReturn(null);

        // When
        accountTransactionService.transfer(transactionRequest);

        // Then
        verify(restTemplate, times(1)).postForLocation(eq(baasURL + "/api/v1/transactions"), any(TransferRequest.class));
    }

    @Test
    void BaaS_이체요청_클라이언트_오류() {
        // Given
        BigDecimal transactionAmount = BigDecimal.valueOf(100);

        Account parentAccount = AccountFixture.builder()
                .accountNumber("PARENT123456")
                .balance(BigDecimal.valueOf(500))
                .state(AccountState.ACTIVE)
                .member(parentMember)
                .build()
                .account();

        Account childAccount = AccountFixture.builder()
                .accountNumber("CHILD123456")
                .balance(BigDecimal.valueOf(200))
                .state(AccountState.ACTIVE)
                .member(childMember)
                .build()
                .account();

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .parentAccountNumber(parentAccount.getAccountNumber())
                .childAccountNumber(childAccount.getAccountNumber())
                .amount(transactionAmount)
                .sender(parentMember.getName())
                .receiver(childMember.getName())
                .build();

        when(accountRepository.findAccountByAccountNumber(parentAccount.getAccountNumber())).thenReturn(Optional.of(parentAccount));
        when(accountRepository.findAccountByAccountNumber(childAccount.getAccountNumber())).thenReturn(Optional.of(childAccount));

        doThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "잘못된 요청"))
                .when(restTemplate).postForLocation(eq(baasURL + "/api/v1/transactions"), any(TransferRequest.class));

        // When & Then
        WekidsException exception = assertThrows(WekidsException.class, () ->
                accountTransactionService.transfer(transactionRequest)
        );

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.BAAS_REQUEST_FAILED);
        assertThat(exception.getMessage()).contains("BaaS 요청 실패: 400 잘못된 요청");

        verify(restTemplate, times(1)).postForLocation(eq(baasURL + "/api/v1/transactions"), any(TransferRequest.class));
    }

    @Test
    void BaaS_이체요청_네트워크_오류() {
        // Given
        BigDecimal transactionAmount = BigDecimal.valueOf(100);

        Account parentAccount = AccountFixture.builder()
                .accountNumber("PARENT123456")
                .balance(BigDecimal.valueOf(500))
                .state(AccountState.ACTIVE)
                .member(parentMember)
                .build()
                .account();

        Account childAccount = AccountFixture.builder()
                .accountNumber("CHILD123456")
                .balance(BigDecimal.valueOf(200))
                .state(AccountState.ACTIVE)
                .member(childMember)
                .build()
                .account();

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .parentAccountNumber(parentAccount.getAccountNumber())
                .childAccountNumber(childAccount.getAccountNumber())
                .amount(transactionAmount)
                .sender(parentMember.getName())
                .receiver(childMember.getName())
                .build();

        when(accountRepository.findAccountByAccountNumber(parentAccount.getAccountNumber())).thenReturn(Optional.of(parentAccount));
        when(accountRepository.findAccountByAccountNumber(childAccount.getAccountNumber())).thenReturn(Optional.of(childAccount));

        doThrow(new RestClientException("네트워크 오류"))
                .when(restTemplate).postForLocation(eq(baasURL + "/api/v1/transactions"), any(TransferRequest.class));

        // When & Then
        WekidsException exception = assertThrows(WekidsException.class, () ->
                accountTransactionService.transfer(transactionRequest)
        );

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.BAAS_REQUEST_FAILED);
        assertThat(exception.getMessage()).contains("BaaS 요청 실패: 네트워크 오류 또는 알 수 없는 문제 - 네트워크 오류");

        verify(restTemplate, times(1)).postForLocation(eq(baasURL + "/api/v1/transactions"), any(TransferRequest.class));
    }

}
