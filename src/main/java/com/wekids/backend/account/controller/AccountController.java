package com.wekids.backend.account.controller;

import com.wekids.backend.account.dto.response.AccountChildResponse;
import com.wekids.backend.account.dto.response.AccountResponse;
import com.wekids.backend.account.service.AccountService;
import com.wekids.backend.auth.controller.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/children")
    public ResponseEntity<List<AccountChildResponse>> findAccountChildList(@MemberId Long memberId){
        return ResponseEntity.ok(accountService.showChildrenAccountList(memberId));
    }

    @GetMapping("/baas")
    public ResponseEntity<List<AccountResponse>> getAccounts(@MemberId Long memberId){
        List<AccountResponse> allAccountResponses = accountService.showAllAccountList(memberId);
        return ResponseEntity.ok(allAccountResponses);
    }
}
