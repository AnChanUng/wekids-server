package com.wekids.backend.account.dto.response;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountDesignType;
import com.wekids.backend.account.domain.enums.AccountState;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class AccountResponse {
    private Long id;
    private String name;
    private String account_number;
    private BigDecimal amount;
    private AccountState state;
    private AccountDesignType design_type;

    public AccountResponse(Account account){
        this.id = account.getId();
        this.name = account.getMember().getName();
        this.account_number = account.getAccountNumber();
        this.amount = account.getAmount();
        this.state = account.getState();
        this.design_type = account.getDesignType();
    }
}
