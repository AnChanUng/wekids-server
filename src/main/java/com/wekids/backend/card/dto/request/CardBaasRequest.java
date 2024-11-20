package com.wekids.backend.card.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardBaasRequest {
    private String password;
    private Long bankMemberId;
    private String accountNumber;
}
