package com.wekids.backend.baas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class BaasAllAccountResponse {

    private String accountNumber;
    private String bankName;
    private BigDecimal balance;
    private String state;
    private String bankMemberName;
    private String productName;
    private String accountType;

}
