package com.wekids.backend.accountTransaction.controller;

import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.service.AccountTransactionService;
import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;
import com.wekids.backend.accountTransaction.service.AccountTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    @GetMapping("/{transaction_id}")
    public TransactionDetailSearchResponse getTransactionDetails(@PathVariable Long transaction_id) {
        TransactionDetailSearchResponse result = accountTransactionService.findByTransactionId(transaction_id);
        return result;
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<TransactionListResponse> getTransactionList(
            @PathVariable("accountId") Long accountId,
            @RequestParam(value = "start", required = false) String start,
            @RequestParam(value = "end", required = false) String end,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "page", required = false) int page,
            @RequestParam(value = "size", required = false) int size) {
                TransactionListResponse transactionListResponse = accountTransactionService.getTransactionList(accountId, start, end, type, page, size);
            return ResponseEntity.ok(transactionListResponse);
    }
}
