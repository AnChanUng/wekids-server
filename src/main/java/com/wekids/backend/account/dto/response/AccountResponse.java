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

    private String accountNumber;
    private String bankName;
    private BigDecimal balance;

    public AccountResponse(Account account){
        this.accountNumber = account.getAccountNumber();
        this.bankName = "우리은행";
        this.balance = account.getBalance();
    }
}
