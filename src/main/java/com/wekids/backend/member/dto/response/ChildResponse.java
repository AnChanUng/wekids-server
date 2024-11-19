package com.wekids.backend.member.dto.response;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.design.domain.enums.ColorType;
import com.wekids.backend.member.domain.Child;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ChildResponse {
    Long childId;
    String name;
    String accountNumber;
    String profile;
    BigDecimal balance;
    Long accountId;
    AccountState cardState;
    ColorType color;
    CharacterType character;

    public static ChildResponse of(Child child, Account account, Design design){
        return new ChildResponse(
                child.getId(),
                child.getName(),
                account.getAccountNumber(),
                child.getProfile(),
                account.getBalance(),
                account.getId(),
                account.getState(),
                design.getColor(),
                design.getCharacter()
        );
    }
}
