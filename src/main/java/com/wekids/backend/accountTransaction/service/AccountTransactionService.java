package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;

import com.wekids.backend.accountTransaction.dto.response.TransactionGetResponse;
import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountTransactionService {
    TransactionDetailSearchResponse findByTransactionId(Long transactionId);
    List<TransactionGetResponse> showTransactionList(long accountid, LocalDateTime start, LocalDateTime end, String type, int page, int size);

}
