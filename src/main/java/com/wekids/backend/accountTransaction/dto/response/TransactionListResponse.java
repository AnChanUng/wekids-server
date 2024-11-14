package com.wekids.backend.accountTransaction.dto.response;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.dto.result.TransactionListResult;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TransactionListResponse {
    private BigDecimal balance;
    private boolean hasNext;
    private List<TransactionListResult> transactions;

    public TransactionListResponse(Account account, List<TransactionListResult> transactionListResults, boolean hasNext){
        this.balance = account.getBalance();
        this.hasNext = hasNext;
        this.transactions = transactionListResults;

    }
}
