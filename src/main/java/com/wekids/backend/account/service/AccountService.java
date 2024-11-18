package com.wekids.backend.account.service;

import com.wekids.backend.account.dto.response.AccountChildResponse;

import java.util.List;

public interface AccountService {
    public List<AccountChildResponse> findChildrenAccountList(long parentId);
}
