package com.wekids.backend.account.controller;

import com.wekids.backend.account.dto.response.AccountResponse;
import com.wekids.backend.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping()
    public ResponseEntity<List<AccountResponse>> getAccount(){
        List<AccountResponse> responseList = accountService.getAccount();
        return ResponseEntity.ok(responseList);
    }
}
