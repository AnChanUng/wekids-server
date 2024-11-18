package com.wekids.backend.support.fixture;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.design.domain.enums.ColorType;
import com.wekids.backend.member.domain.Member;
import jakarta.persistence.*;

public class DesignFixture {

    private Member member;

    private ColorType color = ColorType.PINK1;

    private CharacterType character = CharacterType.HEARTSPRING;

    private Account account;

    private Card card;

    public static DesignFixture builder() {
        return new DesignFixture();
    }

    public DesignFixture member(Member member) {
        this.member = member;
        return this;
    }

    // Fluent-style setter for color
    public DesignFixture color(ColorType color) {
        this.color = color;
        return this;
    }

    // Fluent-style setter for character
    public DesignFixture character(CharacterType character) {
        this.character = character;
        return this;
    }

    // Fluent-style setter for account
    public DesignFixture account(Account account) {
        this.account = account;
        return this;
    }

    // Fluent-style setter for card
    public DesignFixture card(Card card) {
        this.card = card;
        return this;
    }

    public Design build(){
        return Design.builder().member(member).color(color).character(character).account(account).card(card).build();
    }



}
