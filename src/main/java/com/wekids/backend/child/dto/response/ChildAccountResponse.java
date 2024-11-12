package com.wekids.backend.child.dto.response;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountDesignType;
import com.wekids.backend.child.dto.result.ChildAccountResult;
import com.wekids.backend.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class ChildAccountResponse {
    private String name;
    private String accountNumber;
    private String profile;
    private BigDecimal balance;
    private AccountDesignType designType;
    private Long accountId;
    private List<ChildAccountResult> accounts;

    public ChildAccountResponse(Account account, Member member, List<ChildAccountResult> accounts){

        this.name = member.getName();
        this.accountNumber = account.getAccountNumber();
        this.profile = member.getProfile();
        this.balance = account.getAmount();
        this.designType = account.getDesignType();
        this.accountId = account.getId();
        this.accounts = accounts;
    }
}