package com.wekids.backend.support.fixture;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountTransactionFixture {

    private Long id = 4L;
    private String title = "박민수";
    private String type = "WITHDRAWAL";
    private BigDecimal amount = BigDecimal.valueOf(30000.00).setScale(2);
    private BigDecimal balance = BigDecimal.valueOf(47000.00).setScale(2);
    private String sender = "박민수";
    private String receiver = "김철수";
    private String memo = "친구에게 송금";
    private LocalDateTime createdAt = LocalDateTime.now();
    private Account account;

    public AccountTransactionFixture id(Long id) {
        this.id = id;
        return this;
    }

    public AccountTransactionFixture title(String title) {
        this.title = title;
        return this;
    }

    public AccountTransactionFixture type(String type) {
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

    public AccountTransactionFixture account(Account account){
        this.account = account;
        return this;
    }

    public AccountTransaction build() {
        return AccountTransaction.builder()
                .id(id)
                .title(title)
                .type(TransactionType.valueOf(type))
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
