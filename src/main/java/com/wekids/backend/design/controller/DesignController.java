package com.wekids.backend.design.controller;

import com.wekids.backend.design.dto.request.DesignCreateRequest;
import com.wekids.backend.design.dto.response.DesignResponse;
import com.wekids.backend.design.service.DesignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/design")
@RequiredArgsConstructor
public class DesignController {

    private final DesignService designService;

    private static final Long DEFAULT_MEMBER_ID = 3L;

    @GetMapping
    public ResponseEntity<DesignResponse> getDesign() {
        DesignResponse response = designService.showDesign(DEFAULT_MEMBER_ID);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> createDesign(@RequestBody DesignCreateRequest request) {
        designService.createDesign(DEFAULT_MEMBER_ID, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
