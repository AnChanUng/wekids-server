package com.wekids.backend.support.fixture;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.design.domain.enums.ColorType;
import com.wekids.backend.member.domain.Member;

public class DesignFixture {
    private Long memberId = 2L; // 예시 회원 ID
    private ColorType color = ColorType.PINK1; // 더미 데이터 색상 (Enum)
    private CharacterType character = CharacterType.HEARTSPRING; // 더미 데이터 캐릭터 (Enum)
    private Account account; // 계좌
    private Card card; // 카드
    private Member member; // 회원 정보

    // 빌더 메서드
    public static DesignFixture builder() {
        return new DesignFixture();
    }

    // 필요한 경우, 객체를 반환하는 메서드 추가
    public DesignFixture withMemberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public DesignFixture withColor(ColorType color) {
        this.color = color;
        return this;
    }

    public DesignFixture withCharacter(CharacterType character) {
        this.character = character;
        return this;
    }

    public DesignFixture withAccount(Account account) {
        this.account = account;
        return this;
    }

    public DesignFixture withCard(Card card) {
        this.card = card;
        return this;
    }

    public DesignFixture withMember(Member member) {
        this.member = member;
        return this;
    }

    // 디자인 객체를 생성하는 메서드
    public Design build() {
        return Design.builder()
                .memberId(memberId)
                .color(color)
                .character(character)
                .account(account)
                .card(card)
                .member(member)
                .build();
    }

}
