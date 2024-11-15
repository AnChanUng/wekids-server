package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;

import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;

import java.time.LocalDateTime;

public interface AccountTransactionService {
    TransactionDetailSearchResponse findByTransactionId(Long transactionId);
    TransactionListResponse showTransactionList(long accountid, LocalDateTime start, LocalDateTime end, String type, int page, int size);

}
