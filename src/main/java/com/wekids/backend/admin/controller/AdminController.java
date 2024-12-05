package com.wekids.backend.admin.controller;

import com.wekids.backend.log.dto.reqeust.LogRequestParams;
import com.wekids.backend.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final LogService logService;

    @GetMapping("/logs")
    public ResponseEntity<Map<String, String>> getLogs(@ModelAttribute LogRequestParams requestParams) {
        return ResponseEntity.ok(logService.showLogs(requestParams));
    }

    
}