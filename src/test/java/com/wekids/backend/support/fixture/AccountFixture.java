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
    private Long id= 1L;
    @Builder.Default
    private String accountNumber = "11111-111-1111";
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(100.00);
    @Builder.Default
    private AccountState state = AccountState.ACTIVE;
    @Builder.Default
    private LocalDateTime createAt = LocalDateTime.now();
    private LocalDateTime inactiveDate;
    private Member member;

    public Account from() {
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