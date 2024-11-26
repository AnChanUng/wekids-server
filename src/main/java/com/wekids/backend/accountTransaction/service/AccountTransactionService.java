package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.dto.request.AccountTransactionListGetRequestParams;
import com.wekids.backend.accountTransaction.dto.request.TransactionRequest;
import com.wekids.backend.accountTransaction.dto.request.UpdateMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;

import com.wekids.backend.accountTransaction.dto.response.TransactionHistoryResponse;

public interface AccountTransactionService {
    TransactionDetailSearchResponse showTransaction(Long transactionId);

    void transfer(TransactionRequest transactionRequest);

    void updateMemo(Long transactionId, UpdateMemoRequest request);

    TransactionHistoryResponse showTransactionList(Long accountId, AccountTransactionListGetRequestParams params);
}
