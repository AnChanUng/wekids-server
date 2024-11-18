package com.wekids.backend.support.fixture;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.design.domain.enums.ColorType;
import com.wekids.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;

@Builder
public class DesignFixture {

    private Member member;
    @Builder.Default
    private ColorType color = ColorType.PINK1;
    @Builder.Default
    private CharacterType character = CharacterType.HEARTSPRING;

    private Account account;

    private Card card;



    public Design from(){
        return Design.builder().member(member).color(color).character(character).account(account).card(card).build();
    }



}
