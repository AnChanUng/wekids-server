package com.wekids.backend.admin.dto.response;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Long accountId;
    private String accountNumber;
    private AccountState state;
    private LocalDateTime inactiveDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AccountResponse from(Account account) {
        return AccountResponse.builder()
                .accountId(account.getId())
                .accountNumber(account.getAccountNumber())
                .state(account.getState())
                .inactiveDate(account.getInactiveDate())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
    }
}
