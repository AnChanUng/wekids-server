package com.wekids.backend.member.controller;

import com.wekids.backend.member.dto.response.ParentAccountResponse;
import com.wekids.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/parents")
    public ResponseEntity<ParentAccountResponse> getParentAccount(){
        ParentAccountResponse response = memberService.getParentAccount();
        return ResponseEntity.ok(response);
    }
}
