package com.wekids.backend.accountTransaction.dto.response;

import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TransactionResult {
    private Long accountTransactionId;
    private String title;
    private TransactionType type;
    private BigDecimal amount;
    private BigDecimal balance;
    private LocalDateTime createAt;

    public static TransactionResult of(
            Long accountTransactionId,
            String title,
            TransactionType type,
            BigDecimal amount,
            BigDecimal balance,
            LocalDateTime createAt
    ){
        return TransactionResult.builder()
                .accountTransactionId(accountTransactionId)
                .title(title)
                .type(type)
                .amount(amount)
                .balance(balance)
                .createAt(createAt)
                .build();
    }
}
