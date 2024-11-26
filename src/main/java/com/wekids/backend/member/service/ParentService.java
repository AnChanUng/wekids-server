package com.wekids.backend.member.service;

import com.wekids.backend.member.dto.request.AccountInquiryAgreeRequest;
import com.wekids.backend.member.dto.response.ParentAccountResponse;

public interface ParentService {
    ParentAccountResponse showParentAccount(Long parentId);
    void agreeAccountInquiry(AccountInquiryAgreeRequest accountInquiryAgreeRequest, Long memberId);
}
