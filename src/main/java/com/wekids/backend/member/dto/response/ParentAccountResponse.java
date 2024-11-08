package com.wekids.backend.member.dto.response;


import com.wekids.backend.account.domain.enums.AccountDesignType;
import com.wekids.backend.member.dto.result.ParentAccountResult;
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

}
