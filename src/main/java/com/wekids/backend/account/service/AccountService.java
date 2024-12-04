package com.wekids.backend.account.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.dto.response.AccountChildResponse;
import com.wekids.backend.account.dto.response.AccountResponse;

import java.util.List;

public interface AccountService {
    List<AccountChildResponse> showChildrenAccountList(Long parentId);
    List<AccountResponse> showAllAccountList(Long memberId);
    void updateAccount(Account account);
}
