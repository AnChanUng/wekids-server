package com.wekids.backend.baas.dto.response;

import com.wekids.backend.account.dto.response.BaasAccountResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class AllAccountResponse {

    private String accountNumber;
    private String bankName;
    private BigDecimal balance;

    public AllAccountResponse(BaasAccountResponse baasAccountResponse){
        this.accountNumber = baasAccountResponse.getAccountNumber();
        this.bankName = baasAccountResponse.getBankName();
        this.balance = baasAccountResponse.getBalance();
    }
}
