package com.wekids.backend.member.controller;

import com.wekids.backend.auth.controller.MemberId;
import com.wekids.backend.member.dto.request.AccountInquiryAgreeRequest;
import com.wekids.backend.member.dto.response.ParentAccountResponse;
import com.wekids.backend.member.service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parents")
@RequiredArgsConstructor
public class ParentController {
    private final ParentService parentService;

    @GetMapping
    public ResponseEntity<ParentAccountResponse> showParentInfo(@MemberId Long memberId){
        ParentAccountResponse response = parentService.showParentAccount(memberId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/agree-account-inquiry")
    public ResponseEntity<Void> agreeAccountInquiry(@RequestBody @Valid AccountInquiryAgreeRequest accountInquiryAgreeRequest, @MemberId Long memberId) {
        parentService.agreeAccountInquiry(accountInquiryAgreeRequest, memberId);
        return ResponseEntity.noContent().build();
    }
}
