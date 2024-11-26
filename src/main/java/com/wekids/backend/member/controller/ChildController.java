package com.wekids.backend.member.controller;

import com.wekids.backend.auth.controller.MemberId;
import com.wekids.backend.member.dto.response.ChildResponse;
import com.wekids.backend.member.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChildController {
    private final ChildService childService;
    @GetMapping("/children")
    public ResponseEntity<ChildResponse> getParentAccount(@MemberId Long memberId) {
        ChildResponse response = childService.getChildAccount(memberId);
        return ResponseEntity.ok(response);
    }

}
