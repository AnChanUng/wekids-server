package com.wekids.backend.account.dto.response;

import com.wekids.backend.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
public class AccountChildListResponse {

    private final String name;
    private final String acocuntNumber;
    private final String profile;
    private final long accountId;

    public static AccountChildListResponse from(Account account){
        return new AccountChildListResponse(
                account.getMember().getName(),
                account.getAccountNumber(),
                account.getMember().getProfile(),
                account.getId()
        );
    }
}