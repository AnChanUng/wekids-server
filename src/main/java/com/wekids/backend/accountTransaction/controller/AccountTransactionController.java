package com.wekids.backend.accountTransaction.controller;

import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.service.AccountTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
