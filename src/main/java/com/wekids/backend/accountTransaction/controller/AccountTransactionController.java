package com.wekids.backend.accountTransaction.controller;

import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;
import com.wekids.backend.accountTransaction.service.AccountTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    @GetMapping("/api/v1/accounts/{accountId}/transactions")
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
