package com.wekids.backend.card.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountBaasRequest {
    private Long bankMemberId;
    private Long productId;
    private String password;
}
