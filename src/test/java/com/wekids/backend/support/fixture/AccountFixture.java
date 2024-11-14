package com.wekids.backend.support.fixture;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.member.domain.Member;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountFixture {
    private Long id = 1L;
    private String accountNumber = "강현우"; // 더미 데이터 계좌 번호
    private BigDecimal balance = BigDecimal.valueOf(100.00); // 초기 잔액
    private AccountState state = AccountState.ACTIVE; // 계좌 상태
    private LocalDateTime createAt = LocalDateTime.now(); // 생성 시간
    private LocalDateTime inactiveDate; // 비활성화 날짜
    private Member member; // 회원 정보

    public static AccountFixture builder() {
        return new AccountFixture();
    }

    public AccountFixture id(Long id) {
        this.id = id;
        return this;
    }

    public AccountFixture accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public AccountFixture balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public AccountFixture state(AccountState state) {
        this.state = state;
        return this;
    }

    public AccountFixture inactiveDate(LocalDateTime inactiveDate) {
        this.inactiveDate = inactiveDate;
        return this;
    }

    public AccountFixture member(Member member) {
        this.member = member;
        return this;
    }

    public Account build() {
        return Account.builder()
                .id(id)
                .accountNumber(accountNumber)
                .balance(balance)
                .state(state)
                .inactiveDate(inactiveDate)
                .member(member)
                .build();
    }
}
