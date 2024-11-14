package com.wekids.backend.support.fixture;

import com.wekids.backend.account.domain.enums.AccountState;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class AccountFixture {
    private Long id = 1L;
    private String accountNumber = "강현우";
    private BigDecimal balance = BigDecimal.valueOf(100.50);
    private AccountState state = AccountState.ACTIVE;
    private LocalDateTime createAt = LocalDateTime.now();
    private MemberFixture memberFixture = new MemberFixture();

    public AccountFixture builder(){
        return new AccountFixture();
    }

//    public Account build() {
//        return TransactionDetailSearchResponse.from(A)
//                .id(id)
//                .email(email)
//                .name(name)
//                .image(image)
//                .state(state)
//                .course(course)
//                .password(password)
//                .createdAt(createdAt)
//                .sleepDate(sleepDate)
//                .build();
//    }
}
