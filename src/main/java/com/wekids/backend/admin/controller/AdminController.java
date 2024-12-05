package com.wekids.backend.admin.controller;

import com.wekids.backend.account.service.AccountService;
import com.wekids.backend.admin.dto.request.AccountStateRequest;
import com.wekids.backend.admin.dto.request.CardStateRequest;
import com.wekids.backend.admin.dto.response.MemberInfoResponse;
import com.wekids.backend.admin.service.AdminService;
import com.wekids.backend.card.domain.enums.CardState;
import com.wekids.backend.card.service.CardService;
import com.wekids.backend.log.dto.reqeust.LogRequestParams;
import com.wekids.backend.log.service.LogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final LogService logService;
    private final AdminService adminService;
    private final CardService cardService;
    private final AccountService accountService;

    @GetMapping("/logs")
    public ResponseEntity<Map<String, String>> getLogs(@ModelAttribute LogRequestParams requestParams) {
        return ResponseEntity.ok(logService.showLogs(requestParams));
    }

    @GetMapping("/members")
    public ResponseEntity<MemberInfoResponse> getMember(@RequestParam("name") String name, @RequestParam("phone") String phone) {
        MemberInfoResponse memberInfoResponse = adminService.showMember(name, phone);
        return ResponseEntity.ok(memberInfoResponse);
    }

    @PostMapping("/cards/{cardId}/state")
    public ResponseEntity<Void> changeCardState(@PathVariable("cardId") Long cardId, @Valid @RequestBody CardStateRequest request) {
        cardService.changeState(cardId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/accounts/{accountId}/state")
    public ResponseEntity<Void> changeAccountState(@PathVariable("accountId") Long accountId, @Valid @RequestBody AccountStateRequest request) {
        accountService.updateAccountState(accountId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}