package com.wekids.backend.child.dto.result;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountDesignType;
import com.wekids.backend.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ChildAccountResult {
    private Long childId;
    private String name;
    private String accountNumber;
    private String profile;
    private BigDecimal balance;
    private AccountDesignType designType;
    private Long accountId;

    public ChildAccountResult(Account account, Member member){
        this.childId = member.getId();
        this.name = member.getName();
        this.accountNumber = account.getAccountNumber();
        this.profile = member.getProfile();
        this.balance = account.getAmount();
        this.designType = account.getDesignType();
        this.accountId = account.getId();
    }
}
