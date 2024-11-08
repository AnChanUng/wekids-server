package com.wekids.backend.member.dto.result;

import com.wekids.backend.account.domain.enums.AccountDesignType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ParentAccountResult {
    private Long childId;
    private String name;
    private String accountNumber;
    private String profile;
    private BigDecimal balance;
    private AccountDesignType designType;
    private Long accountId;
}
