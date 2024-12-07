package com.wekids.backend.admin.controller;

import com.wekids.backend.account.service.AccountService;
import com.wekids.backend.admin.dto.request.AccountStateRequest;
import com.wekids.backend.admin.dto.request.CardStateRequest;
import com.wekids.backend.admin.dto.request.MemberSimplePasswordRequest;
import com.wekids.backend.admin.dto.request.MemberStateRequest;
import com.wekids.backend.admin.dto.response.InActiveDateResponse;
import com.wekids.backend.admin.dto.response.MemberInfoResponse;
import com.wekids.backend.admin.service.AdminService;
import com.wekids.backend.card.service.CardService;
import com.wekids.backend.log.dto.reqeust.LogRequestParams;
import com.wekids.backend.log.service.LogService;
import com.wekids.backend.member.service.MemberService;
import com.wekids.backend.member.service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final LogService logService;
    private final AdminService adminService;
    private final CardService cardService;
    private final AccountService accountService;
    private final MemberService memberService;
    private final ParentService parentService;

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
    public ResponseEntity<InActiveDateResponse> changeCardState(@PathVariable("cardId") Long cardId, @Valid @RequestBody CardStateRequest request) {
        InActiveDateResponse response = cardService.changeState(cardId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/accounts/{accountId}/state")
    public ResponseEntity<InActiveDateResponse> changeAccountState(@PathVariable("accountId") Long accountId, @Valid @RequestBody AccountStateRequest request) {
        InActiveDateResponse response = accountService.changeAccountState(accountId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/members/{memberId}/state")
    public ResponseEntity<InActiveDateResponse> changeMemberState(@PathVariable("memberId") Long memberId, @Valid @RequestBody MemberStateRequest request) {
        InActiveDateResponse response = memberService.changeMemberState(memberId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/parent/{parentId}/password")
    public ResponseEntity<Void> changeMemberSimplePassword(@PathVariable("parentId") Long parentId, @Valid @RequestBody MemberSimplePasswordRequest request) {
        parentService.changeSimplePassword(parentId, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}