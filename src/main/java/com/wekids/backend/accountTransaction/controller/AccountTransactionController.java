package com.wekids.backend.accountTransaction.controller;

import com.wekids.backend.accountTransaction.dto.request.UpdateMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.dto.response.TransactionGetResponse;
import com.wekids.backend.accountTransaction.service.AccountTransactionService;
import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;
import com.wekids.backend.accountTransaction.service.AccountTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    @PostMapping("/{transactionId}/memo")
    public ResponseEntity<Void> saveMemo(@PathVariable("transactionId") Long transactionId, @RequestBody @Valid UpdateMemoRequest request) {
        accountTransactionService.saveMemo(transactionId, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDetailSearchResponse> getTransactionDetails(@PathVariable("transactionId") Long transactionId) {
        TransactionDetailSearchResponse result = accountTransactionService.findByTransactionId(transactionId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<TransactionGetResponse>> getTransactionList(
            @PathVariable("accountId") Long accountId,
            @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime end,
            @RequestParam(value = "type", required = false, defaultValue = "ALL") String type,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        Slice<TransactionGetResponse> responseList = accountTransactionService.showTransactionList(accountId, start, end, type, page, size);
            return ResponseEntity.ok(responseList.getContent());
    }
}
