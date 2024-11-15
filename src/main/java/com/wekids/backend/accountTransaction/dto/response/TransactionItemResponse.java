package com.wekids.backend.accountTransaction.dto.response;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransactionItemResponse {

    private long accountTransactionId;
    private String title;
    private TransactionType type;
    private BigDecimal amount;
    private BigDecimal balance;
    private LocalDateTime create_at;

    public static TransactionItemResponse from(AccountTransaction accountTransaction) {
        return new TransactionItemResponse(
                accountTransaction.getId(),
                accountTransaction.getTitle(),
                accountTransaction.getType(),
                accountTransaction.getAmount(),
                accountTransaction.getBalance(),
                accountTransaction.getCreatedAt()
        );
    }


}
