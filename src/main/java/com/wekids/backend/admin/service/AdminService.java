package com.wekids.backend.admin.service;

import com.wekids.backend.admin.dto.response.MemberInfoResponse;

public interface AdminService {
    public MemberInfoResponse showMember(String name, String phone);
}
