package com.wekids.backend.support.fixture;

import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.domain.enums.CardState;
import com.wekids.backend.member.domain.enums.MemberState;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberFixture {

    private Long id = 1L; // 예시 ID
    private String name = "조예은"; // 더미 데이터 이름
    private String email = "1234"; // 더미 데이터 이메일
    private String simplePassword = "12345"; // 더미 데이터 비밀번호
    private String phone = "1"; // 예시 전화번호
    private LocalDate birthday = LocalDate.of(1998, 5, 1); // 예시 생일
    private String profile = "https://image.com/virtual.png"; // 프로필 이미지 URL
    private MemberState state = MemberState.ACTIVE; // 상태
    private LocalDateTime inactiveDate = null; // 비활성화 날짜는 null로 설정
    private CardState cardState = CardState.NONE; // 카드 상태
    private Long bankMemberId = null; // 은행 회원 ID는 null로 설정


    // 빌더 메서드
    public static MemberFixture builder() {
        return new MemberFixture();
    }

    // 필요한 경우, 객체를 반환하는 메서드 추가
    public MemberFixture id(Long id) {
        this.id = id;
        return this;
    }

    public MemberFixture name(String name) {
        this.name = name;
        return this;
    }

    public MemberFixture email(String email) {
        this.email = email;
        return this;
    }

    public MemberFixture simplePassword(String simplePassword) {
        this.simplePassword = simplePassword;
        return this;
    }

    public MemberFixture phone(String phone) {
        this.phone = phone;
        return this;
    }

    public MemberFixture birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public MemberFixture profile(String profile) {
        this.profile = profile;
        return this;
    }

    public MemberFixture state(MemberState state) {
        this.state = state;
        return this;
    }

    public MemberFixture inactiveDate(LocalDateTime inactiveDate) {
        this.inactiveDate = inactiveDate;
        return this;
    }

    public MemberFixture cardState(CardState cardState) {
        this.cardState = cardState;
        return this;
    }

    public MemberFixture bankMemberId(Long bankMemberId) {
        this.bankMemberId = bankMemberId;
        return this;
    }


    // Member 객체를 반환하는 메서드 (예시)
    public Member build() {
        return Member.builder()
                .id(id)
                .name(name)
                .phone(phone)
                .birthday(birthday)
                .profile(profile)
                .email(email)
                .simplePassword(simplePassword)
                .state(state)
                .inactiveDate(inactiveDate)
                .cardState(cardState)
                .bankMemberId(bankMemberId)
                .build();
    }
}
