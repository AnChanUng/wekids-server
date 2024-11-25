package com.wekids.backend.support.fixture;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.member.domain.Member;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class AccountFixture {
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String accountNumber = "1002-123-456789"; // 더미 데이터 계좌 번호
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(100.00); // 초기 잔액
    @Builder.Default
    private AccountState state = AccountState.ACTIVE; // 계좌 상태
    @Builder.Default
    private LocalDateTime createAt = LocalDateTime.now(); // 생성 시간
    private LocalDateTime inactiveDate; // 비활성화 날짜
    @Builder.Default
    private Member member = ChildFixture.builder().build().child(); // 회원 정보

    public Account account() {
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
