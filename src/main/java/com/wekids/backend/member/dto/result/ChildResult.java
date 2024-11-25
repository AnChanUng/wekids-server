package com.wekids.backend.member.dto.result;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ChildResult {
    private Long childId;
    private String name;
    private String accountNumber;
    private String profile;
    private BigDecimal balance;
    private Long accountId;
    private String CardState;
    private String color;
    private String character;

    public ChildResult(Long childId, String name, String accountNumber, String profile,
                       BigDecimal balance, Long accountId, String CardState,
                       String color, String character) {
        this.childId = childId;
        this.name = name;
        this.accountNumber = accountNumber;
        this.profile = profile;
        this.balance = balance;
        this.accountId = accountId;
        this.CardState = CardState;
        this.color = color;
        this.character = character;
    }
}
