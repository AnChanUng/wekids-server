package com.wekids.backend.baas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardCreateRequest {
    private String password;
    private Long bankMemberId;
    private String accountNumber;

    public static CardCreateRequest of(String accountNumber, Long bankMemberId, String cardPassword) {
        return CardCreateRequest.builder()
                .accountNumber(accountNumber)
                .bankMemberId(bankMemberId)
                .password(cardPassword)
                .build();
    }
}
