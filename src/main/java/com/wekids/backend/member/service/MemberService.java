package com.wekids.backend.member.service;


import com.wekids.backend.admin.dto.request.MemberStateRequest;

public interface MemberService {
    public void changeMemberState(Long memberId, MemberStateRequest request);
}
