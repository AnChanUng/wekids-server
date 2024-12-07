package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.dto.request.AccountTransactionListGetRequestParams;
import com.wekids.backend.accountTransaction.dto.request.TransactionRequest;
import com.wekids.backend.accountTransaction.dto.request.UpdateMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;

import com.wekids.backend.accountTransaction.dto.response.TransactionHistoryResponse;
import com.wekids.backend.accountTransaction.dto.response.TransferResultResponse;
import com.wekids.backend.mission.domain.Mission;

public interface AccountTransactionService {
    TransactionDetailSearchResponse showTransaction(Long transactionId);

    TransferResultResponse transfer(TransactionRequest transactionRequest);

    TransferResultResponse transfer(Mission mission, String simplePassword);

    void updateMemo(Long transactionId, UpdateMemoRequest request);

    TransactionHistoryResponse showTransactionList(Long accountId, AccountTransactionListGetRequestParams params);
}
