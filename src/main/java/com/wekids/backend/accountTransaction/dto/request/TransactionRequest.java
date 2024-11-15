package com.wekids.backend.accountTransaction.dto.request;

import lombok.Builder;

@Builder
public class TransactionRequest {
    private String parentAccountNumber;
    private String childAccountNumber;
    private Long amount;    //이체 금액
    private String sender;  //부모이름
    private String receiver;    //자식이름
}
