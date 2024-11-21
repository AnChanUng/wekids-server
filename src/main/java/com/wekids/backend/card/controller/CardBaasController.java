package com.wekids.backend.card.controller;

import com.wekids.backend.card.dto.request.PasswordRequest;
import com.wekids.backend.card.service.CardBaasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts/cards")
@RequiredArgsConstructor
public class CardBaasController {

    private final CardBaasService cardBaasService;

    @PostMapping("/issue")
    public ResponseEntity<Void> issueCard(@Valid @RequestBody PasswordRequest passwordRequest) {
        cardBaasService.issueAccountAndCard(passwordRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}