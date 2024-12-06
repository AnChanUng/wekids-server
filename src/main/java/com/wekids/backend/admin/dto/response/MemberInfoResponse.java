package com.wekids.backend.admin.dto.response;


import com.wekids.backend.account.domain.Account;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse {
    private MemberResponse member;
    private CardResponse card;
    private AccountResponse account;
    private DesignResponse design;
    private List<FamilyResponse> family;

    public static MemberInfoResponse of(
        Member member,
        Card card,
        Account account,
        Design design,
        List<Member> members
    ){
        MemberInfoResponseBuilder builder = MemberInfoResponse.builder();
        builder.member(MemberResponse.from(member));

        if(account != null) builder.account(AccountResponse.from(account));

        if(card != null) builder.card(CardResponse.from(card));

        if(design != null) builder.design(DesignResponse.from(design));

       builder.family(members.stream().map(FamilyResponse::from).toList());

       return builder.build();
    }
}
