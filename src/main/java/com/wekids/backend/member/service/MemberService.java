package com.wekids.backend.member.service;


import com.wekids.backend.admin.dto.request.MemberStateRequest;
import com.wekids.backend.admin.dto.response.InActiveDateResponse;
import com.wekids.backend.member.dto.response.MyPageResponse;

public interface MemberService {
    public InActiveDateResponse changeMemberState(Long memberId, MemberStateRequest request);
    public MyPageResponse showMyPage(Long memberId);
}
