package com.wekids.backend.design.domain;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.design.domain.enums.ColorType;
import com.wekids.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Design {
    @Id
    private Long memberId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @MapsId
    private Member member;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ColorType color = ColorType.BLUE;

    @Column(name = "`character`", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CharacterType character = CharacterType.DADAPING;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    public static Design create(Member member, ColorType color, CharacterType character) {
        return Design.builder()
                .member(member)
                .color(color)
                .character(character)
                .build();
    }

    public void updateAccount(Account account) {
        this.account = account;
    }

    public void updateCard(Card card) {
        this.card = card;
    }
}
