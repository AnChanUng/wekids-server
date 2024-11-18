package com.wekids.backend.support.fixture;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class AccountFixture {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String accountNumber = "account1234567"; // 더미 데이터 계좌 번호

    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(100.00); // 초기 잔액

    @Builder.Default
    private AccountState state = AccountState.ACTIVE; // 계좌 상태

    @Builder.Default
    private LocalDateTime createAt = LocalDateTime.now(); // 생성 시간

    private LocalDateTime inactiveDate; // 비활성화 날짜
    private Member member; // 회원 정보

    public Account toAccount() {
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
