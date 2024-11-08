package com.wekids.backend.member.controller;

import com.wekids.backend.member.dto.request.ParentAccountRequest;
import com.wekids.backend.member.dto.response.ParentAccountResponse;
import com.wekids.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/v1/parents")
    public ParentAccountResponse getParent_Account(@RequestBody ParentAccountRequest parentAccountRequest){
        return memberService.getParent_Account(parentAccountRequest);
    }
}
