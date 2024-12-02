package com.wekids.backend.account.dto.response;

import com.wekids.backend.baas.dto.response.AccountGetResponse;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private String accountNumber;
    private String bankName;
    private Long balance;

    public static AccountResponse from(AccountGetResponse accountGetResponse) {
        return AccountResponse.builder()
                .accountNumber(accountGetResponse.getAccountNumber())
                .bankName(accountGetResponse.getBankName())
                .balance(accountGetResponse.getBalance())
                .build();
    }

    public static List<AccountResponse> from(List<AccountGetResponse> accountGetResponses) {
        return accountGetResponses.stream()
                .map(AccountResponse::from)
                .collect(Collectors.toList());
    }
}
