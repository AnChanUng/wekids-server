package com.wekids.backend.member.service;

import com.wekids.backend.member.dto.request.ParentAccountRequest;
import com.wekids.backend.member.dto.response.ParentAccountResponse;

public interface MemberService {
    public ParentAccountResponse getParent_Account(ParentAccountRequest parentAccountRequest);
}
