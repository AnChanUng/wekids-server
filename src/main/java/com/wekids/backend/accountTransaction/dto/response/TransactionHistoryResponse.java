package com.wekids.backend.accountTransaction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TransactionHistoryResponse {
    private final Long balance;
    private final Boolean hasNext;
    private final List<TransactionResult> transactions;

    public static TransactionHistoryResponse of(Long balance, Boolean hasNext, List<TransactionResult> transactions){
        return new TransactionHistoryResponse(balance, hasNext, transactions);
    }
}
