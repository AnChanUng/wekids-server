package com.wekids.backend.accountTransaction.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class BaaSTransferRequest {
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal amount;

    public static BaaSTransferRequest of(String senderAccNym, String receiverAccNum, BigDecimal amount){
        return new BaaSTransferRequest(senderAccNym, receiverAccNum, amount);
    }
}
