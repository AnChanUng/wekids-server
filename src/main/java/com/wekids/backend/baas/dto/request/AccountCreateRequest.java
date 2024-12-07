package com.wekids.backend.baas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequest {
    private Long bankMemberId;
    private Long productId;
    private String password;

    public static AccountCreateRequest of(Long bankMemberId, String accountPassword) {
        return AccountCreateRequest.builder()
                .bankMemberId(bankMemberId)
                .password(accountPassword)
                .build();
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
