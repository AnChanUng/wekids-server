package com.wekids.backend.baas.dto.response;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {
    private AccountTransactionResponse sender;
    private AccountTransactionResponse receiver;

    public static TransferResponse of(AccountTransactionResponse sender, AccountTransactionResponse receiver) {
        return TransferResponse.builder()
                .sender(sender)
                .receiver(receiver)
                .build();
    }
}
