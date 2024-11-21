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


    public static AccountBaasRequest createFromMemberAndParent(Member member, Parent parent, Long productId) {
        return AccountBaasRequest.builder()
                .bankMemberId(member.getBankMemberId())
                .productId(productId)
                .password(parent.getSimplePassword())
                .build();
    }
}
