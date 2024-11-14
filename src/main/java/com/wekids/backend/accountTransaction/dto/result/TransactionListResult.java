package com.wekids.backend.accountTransaction.dto.result;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionListResult {

    private long accountTransactionId;
    private String title;
    private TransactionType type;
    private BigDecimal amount;
    private BigDecimal balance;
    private LocalDateTime create_at;

    public TransactionListResult(AccountTransaction accountTransaction){
        this.accountTransactionId = accountTransaction.getId();
        this.title = accountTransaction.getTitle();
        this.type = accountTransaction.getType();
        this.amount = accountTransaction.getAmount();
        this.balance = accountTransaction.getBalance();
        this.create_at = accountTransaction.getCreatedAt();
    }


}
