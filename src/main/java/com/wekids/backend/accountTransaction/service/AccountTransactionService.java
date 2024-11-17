package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.dto.request.TransactionRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;

public interface AccountTransactionService {
    TransactionDetailSearchResponse findByTransactionId(Long transactionId);

    void saveTransaction(TransactionRequest transactionRequest);
}
