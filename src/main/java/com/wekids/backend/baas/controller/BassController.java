package com.wekids.backend.baas.controller;

import com.wekids.backend.baas.dto.response.AllAccountResponse;
import com.wekids.backend.baas.service.BaasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BassController {
    private final BaasService baasService;

    @GetMapping("/accounts/baas")
    public ResponseEntity<List<AllAccountResponse>> getAccounts(){
        List<AllAccountResponse> allAccountResponses = baasService.showAccounts(1L);
        return ResponseEntity.ok(allAccountResponses);
    }
}

