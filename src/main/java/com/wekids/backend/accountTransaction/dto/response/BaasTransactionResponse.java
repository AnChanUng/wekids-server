package com.wekids.backend.accountTransaction.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaasTransactionResponse {
    private String title;
    private String type;
    private Long amount;
    private Long balance;
    private String sender;
    private String receiver;
    private String currencyCode;
    private LocalDateTime transactionDate;
}
