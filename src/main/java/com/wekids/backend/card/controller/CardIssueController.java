package com.wekids.backend.card.controller;

import com.wekids.backend.card.dto.request.IssueRequest;
import com.wekids.backend.card.service.CardIssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts/cards/issue")
@RequiredArgsConstructor
public class CardIssueController {
    private final CardIssueService cardBaasService;

    @PostMapping
    public ResponseEntity<Void> issueCard(@Valid @RequestBody IssueRequest issueRequest) {
        cardBaasService.issueAccountAndCard(issueRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}