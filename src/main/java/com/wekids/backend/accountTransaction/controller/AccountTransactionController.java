package com.wekids.backend.accountTransaction.controller;

import com.wekids.backend.accountTransaction.dto.request.TransactionRequest;
import com.wekids.backend.accountTransaction.dto.enums.TransactionRequestType;
import com.wekids.backend.accountTransaction.dto.request.UpdateMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.dto.response.TransactionHistoryResponse;
import com.wekids.backend.accountTransaction.service.AccountTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    @PostMapping("/{transactionId}/memo")
    public ResponseEntity<Void> saveMemo(@PathVariable("transactionId") Long transactionId, @RequestBody @Valid UpdateMemoRequest request) {
        accountTransactionService.updateMemo(transactionId, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDetailSearchResponse> showTransactionDetails(@PathVariable("transactionId") Long transactionId) {
        TransactionDetailSearchResponse result = accountTransactionService.showTransaction(transactionId);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void> postTransaction(@RequestBody TransactionRequest transactionRequest) {
        accountTransactionService.transfer(transactionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<TransactionHistoryResponse> getTransactionList(
            @PathVariable("accountId") Long accountId,
            @RequestParam(value = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(value = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @RequestParam(defaultValue = "ALL") TransactionRequestType type,
            @PageableDefault(size = 20) Pageable pageable) {
        TransactionHistoryResponse transactionHistoryResponse = accountTransactionService.showTransactionList(accountId, start, end, type, pageable);
        return ResponseEntity.ok(transactionHistoryResponse);
    }
}
