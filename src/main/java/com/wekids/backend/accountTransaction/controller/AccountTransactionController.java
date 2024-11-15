package com.wekids.backend.accountTransaction.controller;

import com.wekids.backend.accountTransaction.dto.request.SaveMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.service.AccountTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    @PostMapping("/{transactionId}/memo")
    public ResponseEntity<Void> saveMemo(@PathVariable("transactionId") Long transactionId, @RequestBody @Valid SaveMemoRequest request) {
        accountTransactionService.saveMemo(transactionId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDetailSearchResponse> getTransactionDetails(@PathVariable("transactionId") Long transactionId) {
        TransactionDetailSearchResponse result = accountTransactionService.findByTransactionId(transactionId);
        return ResponseEntity.ok(result);
    }
}
