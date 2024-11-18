package com.wekids.backend.member.dto.response;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.domain.enums.ColorType;
import com.wekids.backend.member.domain.Parent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ParentResponse {
    String name;
    String accountNumber;
    String profile;
    BigDecimal balance;
    ColorType designType;
    long accountId;

    public static ParentResponse from(Parent parent, Account account, Design design){
        return new ParentResponse(parent.getName(), account.getAccountNumber(), parent.getProfile(), account.getBalance(), design.getColor(), account.getId());
    }
}
