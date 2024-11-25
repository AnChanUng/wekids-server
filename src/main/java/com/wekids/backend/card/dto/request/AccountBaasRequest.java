package com.wekids.backend.card.dto.request;

import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.domain.Parent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountBaasRequest {
    private Long bankMemberId;
    private Long productId;
    private String password;


    public static AccountBaasRequest of(Long bankMemberId, String accountPassword, Long productId) {
        return AccountBaasRequest.builder()
                .bankMemberId(bankMemberId)
                .productId(productId)
                .password(accountPassword)
                .build();
    }
}
