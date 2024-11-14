package com.wekids.backend.accountTransaction.dto.response;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class TransactionDetailSearchResponse {
    private final String title;
    private final TransactionType type;
    private final BigDecimal amount;
    private final BigDecimal balance;
    private final String memo;
    private final LocalDateTime createAt;

    public static TransactionDetailSearchResponse from(AccountTransaction accountTransaction) {
        return new TransactionDetailSearchResponse(
                accountTransaction.getTitle(),
                accountTransaction.getType(),
                accountTransaction.getAmount(),
                accountTransaction.getBalance(),
                accountTransaction.getMemo(),
                accountTransaction.getCreatedAt()
        );
    }
}
