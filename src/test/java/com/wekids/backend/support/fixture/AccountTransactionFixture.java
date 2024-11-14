package com.wekids.backend.support.fixture;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountTransactionFixture {
    private Long id = 1L;
    private String title = "카카오페이"; // 더미 데이터 제목
    private TransactionType type = TransactionType.DEPOSIT; // 기본 거래 유형
    private BigDecimal amount = BigDecimal.valueOf(100.00); // 기본 거래 금액
    private BigDecimal balance = BigDecimal.valueOf(1000.00); // 기본 잔액
    private String sender = "Sender Name"; // 더미 송신자
    private String receiver = "Receiver Name"; // 더미 수신자
    private String memo; // 메모
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 시간
    private Account account; // 계좌 정보

    public static AccountTransactionFixture builder() {
        return new AccountTransactionFixture();
    }

    public AccountTransactionFixture id(Long id) {
        this.id = id;
        return this;
    }

    public AccountTransactionFixture title(String title) {
        this.title = title;
        return this;
    }

    public AccountTransactionFixture type(TransactionType type) {
        this.type = type;
        return this;
    }

    public AccountTransactionFixture amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public AccountTransactionFixture balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public AccountTransactionFixture sender(String sender) {
        this.sender = sender;
        return this;
    }

    public AccountTransactionFixture receiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public AccountTransactionFixture memo(String memo) {
        this.memo = memo;
        return this;
    }

    public AccountTransactionFixture createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AccountTransactionFixture account(Account account) {
        this.account = account;
        return this;
    }

    public AccountTransaction build() {
        return AccountTransaction.builder()
                .id(id)
                .title(title)
                .type(type)
                .amount(amount)
                .balance(balance)
                .sender(sender)
                .receiver(receiver)
                .memo(memo)
                .createdAt(createdAt)
                .account(account)
                .build();
    }
}
