package com.wekids.backend.admin.controller;

import com.wekids.backend.admin.dto.response.MemberInfoResponse;
import com.wekids.backend.admin.service.AdminService;
import com.wekids.backend.log.dto.reqeust.LogRequestParams;
import com.wekids.backend.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final LogService logService;
    private final AdminService adminService;

    @GetMapping("/logs")
    public ResponseEntity<Map<String, String>> getLogs(@ModelAttribute LogRequestParams requestParams) {
        return ResponseEntity.ok(logService.showLogs(requestParams));
    }

    @GetMapping("/members")
    public ResponseEntity<MemberInfoResponse> getMember(@RequestParam("name") String name, @RequestParam("phone") String phone) {
        MemberInfoResponse memberInfoResponse = adminService.showMember(name, phone);
        return ResponseEntity.ok(memberInfoResponse);
    }

}