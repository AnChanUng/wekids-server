package com.wekids.backend.card.dto.request;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardBaasRequest {
    private String password;
    private Long bankMemberId;
    private String accountNumber;

    public static CardBaasRequest createFromAccountAndMember(Account account, Member member, PasswordRequest passwordRequest) {
        return CardBaasRequest.builder()
                .accountNumber(account.getAccountNumber())
                .bankMemberId(member.getBankMemberId())
                .password(passwordRequest.getPassword())
                .build();
    }
}
