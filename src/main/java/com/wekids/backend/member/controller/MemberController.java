package com.wekids.backend.member.controller;

import com.wekids.backend.member.dto.request.ParentAccountRequest;
import com.wekids.backend.member.dto.response.ParentAccountResponse;
import com.wekids.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/v1/parents")
    public ResponseEntity<ParentAccountResponse> getParentAccount(@RequestBody ParentAccountRequest parentAccountRequest){
        ParentAccountResponse response = memberService.getParent_Account(parentAccountRequest);
        return ResponseEntity.ok(response);
    }
}
