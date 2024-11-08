package com.wekids.backend.child.dto.response;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.member.domain.Child;
import lombok.Getter;

@Getter
public class ChildResponseDto {
    private String name;
    private String accountNumber;
    private String profile;
    private String balance;
    private String designType;
    private Long accountId;

    public ChildResponseDto(Child child, Account account) {
        this.name = child.getName();
        this.profile = child.getProfile();

        if (account != null) {
            this.accountNumber = account.getAccountNumber();
            this.balance = account.getAmount().toString();
            this.designType = account.getDesignType().toString();
            this.accountId = account.getId();
        }
    }
}