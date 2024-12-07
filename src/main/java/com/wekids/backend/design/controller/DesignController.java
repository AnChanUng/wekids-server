package com.wekids.backend.design.controller;

import com.wekids.backend.auth.controller.MemberId;
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

    @GetMapping("/{designId}")
    public ResponseEntity<DesignResponse> getDesign(@PathVariable("designId") Long designId) {
        DesignResponse response = designService.showDesign(designId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> createDesign(@RequestBody DesignCreateRequest request, @MemberId Long memberId) {
        designService.createDesign(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
