package com.wekids.backend.account.service;

import com.wekids.backend.account.dto.response.AccountChildListResponse;

import java.util.List;

public interface AccountService {
    public List<AccountChildListResponse> findAccountChildList(long parentId);
}
