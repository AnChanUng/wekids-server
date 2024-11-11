package com.wekids.backend.account.controller;

import com.wekids.backend.account.dto.response.AccountResponse;
import com.wekids.backend.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/api/v1/accounts/{account_id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long account_id){
        return null;
    }
}
