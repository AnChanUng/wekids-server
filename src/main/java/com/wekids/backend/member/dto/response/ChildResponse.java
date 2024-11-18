package com.wekids.backend.member.dto.response;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.member.domain.Child;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ChildResponse {
    private Long childId;
    private String name;
    private String accountNumber;
    private String profile;
    private BigDecimal balance;
    private Long accountId;
    private String CardState;
    private String color;
    private String character;

    public static ChildResponse from(Child child, Account account, Design design){
        return new ChildResponse(
                child.getId(),
                child.getName(),
                account.getAccountNumber(),
                child.getProfile(),
                account.getBalance(),
                account.getId(),
                child.getCardState().name(),
                design.getColor().name(),
                design.getCharacter().name()
        );
    }
}