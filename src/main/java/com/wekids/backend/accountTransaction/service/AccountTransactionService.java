package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.dto.request.TransactionRequest;
import com.wekids.backend.accountTransaction.dto.request.UpdateMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;

public interface AccountTransactionService {
    TransactionDetailSearchResponse showTransaction(Long transactionId);

    void transfer(TransactionRequest transactionRequest);

    void updateMemo(Long transactionId, UpdateMemoRequest request);
}
