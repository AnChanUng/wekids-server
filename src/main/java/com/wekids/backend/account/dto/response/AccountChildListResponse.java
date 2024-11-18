package com.wekids.backend.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountChildListResponse {

    private final String name;
    private final String acocuntNumber;
    private final String profile;
    private final long accountId;

}