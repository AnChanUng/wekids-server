package com.wekids.backend.member.controller;

import com.wekids.backend.auth.controller.MemberId;
import com.wekids.backend.member.dto.response.MyPageResponse;
import com.wekids.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping
    public ResponseEntity<MyPageResponse> showParentInfo(@MemberId Long memberId){
        MyPageResponse response = memberService.showMyPage(memberId);
        return ResponseEntity.ok(response);
    }
}
