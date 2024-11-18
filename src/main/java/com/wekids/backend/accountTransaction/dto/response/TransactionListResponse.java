package com.wekids.backend.accountTransaction.dto.response;

import com.wekids.backend.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TransactionListResponse {
    private final BigDecimal balance;
    private final boolean hasNext;
    private final List<TransactionItemResponse> transactions;

    public static TransactionListResponse from(BigDecimal balance, List<TransactionItemResponse> transactionListResults, boolean hasNext) {
        return new TransactionListResponse(balance, hasNext, transactionListResults);
    }
}
