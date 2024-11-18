package com.wekids.backend.support.fixture;

import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.enums.CardState;
import com.wekids.backend.member.domain.enums.MemberState;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public class ParentFixture {

    private Long id;
    @Builder.Default
    private String name = "조예은"; // 더미 데이터 이름
    @Builder.Default
    private String email = "1234"; // 더미 데이터 이메일
    @Builder.Default
    private String simplePassword = "12345"; // 더미 데이터 비밀번호
    @Builder.Default
    private String phone = "1"; // 예시 전화번호
    @Builder.Default
    private LocalDate birthday = LocalDate.of(1998, 5, 1); // 예시 생일
    @Builder.Default
    private String profile = "https://image.com/virtual.png"; // 프로필 이미지 URL
    @Builder.Default
    private MemberState state = MemberState.ACTIVE; // 상태
    @Builder.Default
    private LocalDateTime inactiveDate = null; // 비활성화 날짜는 null로 설정
    @Builder.Default
    private CardState cardState = CardState.NONE; // 카드 상태
    @Builder.Default
    private Long bankMemberId = null; // 은행 회원 ID는 null로 설정

    public Parent from() {
        return Parent.builder()
                .id(id)
                .name(name)
                .email(email)
                .simplePassword(simplePassword)
                .phone(phone)
                .birthday(birthday)
                .profile(profile)
                .state(state)
                .cardState(cardState)
                .bankMemberId(bankMemberId)
                .inactiveDate(inactiveDate)
                .build();

    }
}

