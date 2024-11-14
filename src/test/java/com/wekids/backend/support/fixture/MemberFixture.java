package com.wekids.backend.support.fixture;

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
    private LocalDate inactiveDate = null; // 비활성화 날짜는 null로 설정
    private CardState cardState = CardState.NONE; // 카드 상태
    private Long bankMemberId = null; // 은행 회원 ID는 null로 설정
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 날짜

    // 빌더 메서드
    public static MemberFixture builder() {
        return new MemberFixture();
    }

    // 필요한 경우, 객체를 반환하는 메서드 추가
    public MemberFixture withId(Long id) {
        this.id = id;
        return this;
    }

    public MemberFixture withName(String name) {
        this.name = name;
        return this;
    }

    public MemberFixture withEmail(String email) {
        this.email = email;
        return this;
    }

    public MemberFixture withSimplePassword(String simplePassword) {
        this.simplePassword = simplePassword;
        return this;
    }

    public MemberFixture withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public MemberFixture withBirthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public MemberFixture withProfile(String profile) {
        this.profile = profile;
        return this;
    }

    public MemberFixture withState(MemberState state) {
        this.state = state;
        return this;
    }

    public MemberFixture withInactiveDate(LocalDate inactiveDate) {
        this.inactiveDate = inactiveDate;
        return this;
    }

    public MemberFixture withCardState(CardState cardState) {
        this.cardState = cardState;
        return this;
    }

    public MemberFixture withBankMemberId(Long bankMemberId) {
        this.bankMemberId = bankMemberId;
        return this;
    }

    public MemberFixture withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    // Member 객체를 반환하는 메서드 (예시)
//    public Member build() {
//        Member member = new Member();
//        member.setId(this.id);
//        member.setName(this.name);
//        member.setEmail(this.email);
//        member.setSimplePassword(this.simplePassword);
//        member.setPhone(this.phone);
//        member.setBirthday(this.birthday);
//        member.setProfile(this.profile);
//        member.setState(this.state);
//        member.setInactiveDate(this.inactiveDate);
//        member.setCardState(this.cardState);
//        member.setBankMemberId(this.bankMemberId);
//        member.setCreatedAt(this.createdAt);
//        return member;
//    }
}
