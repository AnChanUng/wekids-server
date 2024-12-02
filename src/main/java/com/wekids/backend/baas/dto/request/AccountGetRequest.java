package com.wekids.backend.baas.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountGetRequest {
    private String accountNumber;

    public static AccountGetRequest of(String accountNumber) {
        return new AccountGetRequest(accountNumber);
    }
}
