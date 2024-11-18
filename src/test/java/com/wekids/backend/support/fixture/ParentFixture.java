package com.wekids.backend.support.fixture;

import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.enums.CardState;
import com.wekids.backend.member.domain.enums.MemberState;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class ParentFixture {

    private Long id;
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

    public static ParentFixture builder() {
        return new ParentFixture();
    }

    public ParentFixture id(Long id) {
        this.id = id;
        return this;
    }

    public ParentFixture name(String name) {
        this.name = name;
        return this;
    }

    public ParentFixture email(String email) {
        this.email = email;
        return this;
    }

    public ParentFixture simplePassword(String simplePassword) {
        this.simplePassword = simplePassword;
        return this;
    }

    public ParentFixture phone(String phone) {
        this.phone = phone;
        return this;
    }

    public ParentFixture birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public ParentFixture profile(String profile) {
        this.profile = profile;
        return this;
    }

    public ParentFixture state(MemberState state) {
        this.state = state;
        return this;
    }

    public ParentFixture inactiveDate(LocalDateTime inactiveDate) {
        this.inactiveDate = inactiveDate;
        return this;
    }

    public ParentFixture cardState(CardState cardState) {
        this.cardState = cardState;
        return this;
    }

    public ParentFixture bankMemberId(Long bankMemberId) {
        this.bankMemberId = bankMemberId;
        return this;
    }

    public Parent build() {
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

