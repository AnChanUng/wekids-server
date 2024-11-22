package com.wekids.backend.account.controller;

import com.wekids.backend.account.dto.response.AccountResponse;
import com.wekids.backend.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/baas/accounts")
@RequiredArgsConstructor
public class BassAccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping()
    public ResponseEntity<List<AccountResponse>> getAccount(){
        List<AccountResponse> responseList = accountService.getAccount();
        return ResponseEntity.ok(responseList);
    }
}

