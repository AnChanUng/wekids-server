package com.wekids.backend.support.fixture;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.member.domain.Member;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class AccountFixture {
    private Long id = 1L;
    private String accountNumber = "강현우";
    private BigDecimal balance = BigDecimal.valueOf(100.00);
    private AccountState state = AccountState.ACTIVE;
    private LocalDateTime createAt = LocalDateTime.now();
    private Member memberFixture = MemberFixture.builder().build();

    public Account builder() {
        return Account.builder()
                .id(id)
                .accountNumber(accountNumber)
                .balance(balance)
                .state(state)
                .inactiveDate(createAt)
                .member(memberFixture)
                .build();
    }


}
