package com.wekids.backend.support.fixture;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.design.domain.enums.ColorType;
import com.wekids.backend.member.domain.Member;
import lombok.Builder;

@Builder
public class DesignFixture {
    @Builder.Default
    private long id = 1L;
    @Builder.Default
    private Member member = null;
    @Builder.Default
    private ColorType color = ColorType.PINK1;
    @Builder.Default
    private CharacterType character = CharacterType.HEARTSPRING;
    @Builder.Default
    private Account account = null;
    @Builder.Default
    private Card card = null;

    public Design from() {
        return Design.builder()
                .id(id)
                .member(member)
                .color(color)
                .character(character)
                .account(account)
                .card(card)
                .build();
    }
}
