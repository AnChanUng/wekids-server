package com.wekids.backend.member.dto.response;


import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountDesignType;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.dto.request.ParentAccountRequest;
import com.wekids.backend.member.dto.result.ParentAccountResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class ParentAccountResponse {
    private String name;
    private String accountNumber;
    private String profile;
    private BigDecimal balance;
    private AccountDesignType designType;
    private Long accountId;
    private List<ParentAccountResult> children;

    public ParentAccountResponse(Account account, Member member, List<ParentAccountResult> children){

                this.name = member.getName();
                this.accountNumber = account.getAccountNumber();
                this.profile = member.getProfile();
                this.balance = account.getAmount();
                this.designType = account.getDesignType();
                this.accountId = account.getId();
                this.children = children;
    }

}
