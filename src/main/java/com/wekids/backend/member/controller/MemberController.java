package com.wekids.backend.member.controller;

import com.wekids.backend.member.dto.response.ParentAccountResponse;
import com.wekids.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parents")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<ParentAccountResponse> showParentInfo(){
        ParentAccountResponse response = memberService.showParentAccount(1L);
        return ResponseEntity.ok(response);
    }
}
