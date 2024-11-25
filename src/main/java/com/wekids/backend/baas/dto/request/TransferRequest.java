package com.wekids.backend.baas.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TransferRequest {
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal amount;

    public static TransferRequest of(String senderAccNym, String receiverAccNum, BigDecimal amount){
        return new TransferRequest(senderAccNym, receiverAccNum, amount);
    }
}
