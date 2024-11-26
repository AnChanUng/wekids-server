package com.wekids.backend.baas.dto.response;

import com.wekids.backend.account.domain.enums.AccountState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountGetResponse {
    private String accountNumber;
    private String bankName;
    private Long balance;
    private AccountState state;
    private String bankMemberName;
    private String productName;
    private String productType;
}
