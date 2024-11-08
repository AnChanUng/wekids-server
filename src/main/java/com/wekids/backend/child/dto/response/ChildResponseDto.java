package com.wekids.backend.child.dto.response;

import com.wekids.backend.member.domain.Child;
import lombok.Getter;

@Getter
public class ChildResponseDto {
    private String name;
    private String accountNumber;
    private String profile;
    private String balance; //
    private String designType;
    private Long accountId;

    public ChildResponseDto(Child child) {
        this.name = child.getName();
        this.profile = child.getProfile();

        if (child.getAccount() != null) {
            this.accountNumber = child.getAccount().getAccountNumber();
            this.balance = child.getAccount().getAmount().toString();
            this.designType = child.getAccount().getDesignType().name();
            this.accountId = child.getAccount().getId();
        }
    }
}
