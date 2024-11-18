package com.wekids.backend.account.controller;

import com.wekids.backend.account.dto.response.AccountChildListResponse;
import com.wekids.backend.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/children")
    public ResponseEntity<List<AccountChildListResponse>> findAccountChildList(){
        long parentId = 1L;
        //추후에 토큰값으로 검사
        return ResponseEntity.ok(accountService.findAccountChildList(1L));
    }
}
