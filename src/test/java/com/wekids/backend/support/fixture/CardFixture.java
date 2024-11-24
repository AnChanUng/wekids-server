package com.wekids.backend.support.fixture;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.card.domain.enums.CardState;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public class CardFixture {
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String cardNumber = "1234-5678-9012-3456";
    @Builder.Default
    private LocalDate validThru = LocalDate.now().plusYears(5);
    @Builder.Default
    private String cvc = "123";
    @Builder.Default
    private String memberName = "강현우";
    @Builder.Default
    private String password = "1234";
    @Builder.Default
    private String cardName = "현우핑";
    @Builder.Default
    private CardState state = CardState.ACTIVE;

    private LocalDateTime inactiveDate;
    @Builder.Default
    private LocalDateTime newDate = LocalDateTime.now();
    @Builder.Default
    private Account account = AccountFixture.builder().build().account();

    public Card card() {
        return Card.builder()
                .id(id)
                .cardNumber(cardNumber)
                .validThru(validThru)
                .cvc(cvc)
                .memberName(memberName)
                .password(password)
                .cardName(cardName)
                .state(state)
                .inactiveDate(inactiveDate)
                .newDate(newDate)
                .account(account)
                .build();
    }
}
