package com.wekids.backend.accountTransaction.dto.request;

import com.wekids.backend.accountTransaction.dto.enums.TransactionRequestType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransactionListGetRequestParams {
    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start = LocalDate.now().minusMonths(3);
    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end = LocalDate.now();
    @Builder.Default
    private TransactionRequestType type = TransactionRequestType.ALL;
    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 20;
}
