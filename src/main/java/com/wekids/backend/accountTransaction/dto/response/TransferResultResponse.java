package com.wekids.backend.accountTransaction.dto.response;

import com.wekids.backend.baas.dto.response.AccountTransactionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferResultResponse {
    private String sender;
    private String receiver;
    private Long amount;
    private LocalDateTime transactionTime;

    public static TransferResultResponse of(AccountTransactionResponse receiverAccountTransaction) {
        return  TransferResultResponse.builder()
                .sender(receiverAccountTransaction.getSender())
                .receiver(receiverAccountTransaction.getReceiver())
                .amount(receiverAccountTransaction.getAmount())
                .transactionTime(receiverAccountTransaction.getTransactionDate())
                .build();
    }
}
