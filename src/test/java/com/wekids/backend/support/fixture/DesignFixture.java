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
    private Long memberId = 2L; // 예시 회원 ID

    @Builder.Default
    private ColorType color = ColorType.PINK1; // 더미 데이터 색상 (Enum)

    @Builder.Default
    private CharacterType character = CharacterType.HEARTSPRING; // 더미 데이터 캐릭터 (Enum)
    private Account account; // 계좌
    private Card card; // 카드
    private Member member; // 회원 정보

    // 디자인 객체를 생성하는 메서드
    public Design design() {
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
