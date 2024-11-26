package com.wekids.backend.member.controller;

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
    public ResponseEntity<ParentAccountResponse> showParentInfo(){
        ParentAccountResponse response = parentService.showParentAccount(1L);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/agree-account-inquiry")
    public ResponseEntity<Void> agreeAccountInquiry(@RequestBody @Valid AccountInquiryAgreeRequest accountInquiryAgreeRequest) {
        parentService.agreeAccountInquiry(accountInquiryAgreeRequest, 1L);
        return ResponseEntity.noContent().build();
    }
}
