package com.wekids.backend.accountTransaction.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;

@Getter
@Builder
public class TransactionRequest {
    @NonNull
    private String parentAccountNumber;
    private String childAccountNumber;
    private BigDecimal amount;    //이체 금액
    private String sender;  //부모이름
    private String receiver;    //자식이름

    private TransactionRequest() {}

    public TransactionRequest(String parentAccountNumber, String childAccountNumber, BigDecimal amount, String sender, String receiver) {
        this.parentAccountNumber = parentAccountNumber;
        this.childAccountNumber = childAccountNumber;
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
    }
}
