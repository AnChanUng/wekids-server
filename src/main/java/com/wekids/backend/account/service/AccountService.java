package com.wekids.backend.account.service;

import com.wekids.backend.account.dto.response.AccountResponse;

public interface AccountService {

    AccountResponse getAccount(Long account_id);
}
