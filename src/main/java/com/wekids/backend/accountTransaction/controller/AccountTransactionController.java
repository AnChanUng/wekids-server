package com.wekids.backend.accountTransaction.controller;

import com.wekids.backend.accountTransaction.dto.request.AccountTransactionListGetRequestParams;
import com.wekids.backend.accountTransaction.dto.request.TransactionRequest;
import com.wekids.backend.accountTransaction.dto.request.UpdateMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.dto.response.TransactionHistoryResponse;
import com.wekids.backend.accountTransaction.service.AccountTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    @PostMapping("/transactions/{transactionId}/memo")
    public ResponseEntity<Void> saveMemo(@PathVariable("transactionId") Long transactionId, @RequestBody @Valid UpdateMemoRequest request) {
        accountTransactionService.updateMemo(transactionId, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<TransactionDetailSearchResponse> showTransactionDetails(@PathVariable("transactionId") Long transactionId) {
        TransactionDetailSearchResponse result = accountTransactionService.showTransaction(transactionId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/transactions")
    public ResponseEntity<Void> postTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
        accountTransactionService.transfer(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<TransactionHistoryResponse> getTransactionList(
            @PathVariable("accountId") Long accountId,
            @ModelAttribute AccountTransactionListGetRequestParams params) {
        TransactionHistoryResponse transactionHistoryResponse = accountTransactionService.showTransactionList(accountId, params);
        return ResponseEntity.ok(transactionHistoryResponse);
    }
}
