package com.wekids.backend.member.service;

import com.wekids.backend.member.dto.response.ParentAccountResponse;

public interface ParentService {
    public ParentAccountResponse showParentAccount(Long parentId);
}
