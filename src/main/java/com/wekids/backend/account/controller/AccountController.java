package com.wekids.backend.account.controller;

import com.wekids.backend.account.dto.response.AccountChildListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    @GetMapping("/children")
    public ResponseEntity<AccountChildListResponse> findAccountChildList(){
        //추후에 토큰값으로 검사
        return null;
    }
}
