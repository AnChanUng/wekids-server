package com.wekids.backend.account.dto.response;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.design.domain.enums.CharacterType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountChildResponse {
    private final String name;
    private final String accountNumber;
    private final CharacterType profile;
    private final long accountId;

    public static AccountChildResponse from(Account account){
        return new AccountChildResponse(
                account.getMember().getName(),
                account.getAccountNumber(),
                account.getMember().getProfile(),
                account.getId()
        );
    }
}