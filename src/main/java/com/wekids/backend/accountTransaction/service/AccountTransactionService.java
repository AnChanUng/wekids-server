package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;

public interface AccountTransactionService {
    public TransactionListResponse getTransactionList(long accountid, String start, String end, String type, int page, int size);

}
