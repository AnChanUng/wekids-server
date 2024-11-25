package com.wekids.backend.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class BaasAccountResponse {

    private String accountNumber;
    private String bankName;
    private BigDecimal balance;
    private String state;
    private String bankMemberName;
    private String productName;
    private String accountType;

}
