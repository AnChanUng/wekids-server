package com.wekids.backend.accountTransaction.controller;

import com.wekids.backend.accountTransaction.dto.request.TransactionRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.service.AccountTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDetailSearchResponse> getTransactionDetails(@PathVariable Long transactionId) {
        accountTransactionService.findByTransactionId(transactionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> postTransaction(@RequestBody TransactionRequest transactionRequest) {
        accountTransactionService.saveTransaction(transactionRequest);
        return ResponseEntity.noContent().build();
    }
}
