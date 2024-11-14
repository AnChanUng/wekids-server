package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;

import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;

public interface AccountTransactionService {
    TransactionDetailSearchResponse findByTransactionId(Long transactionId);
    TransactionListResponse getTransactionList(long accountid, String start, String end, String type, int page, int size);

}
