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

    public AccountResponse(BaasAccountResponse baasAccountResponse){
        this.accountNumber = baasAccountResponse.getAccountNumber();
        this.bankName = baasAccountResponse.getBankName();
        this.balance = baasAccountResponse.getBalance();
    }
}
