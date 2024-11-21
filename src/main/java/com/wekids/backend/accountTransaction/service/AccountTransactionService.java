package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;

import com.wekids.backend.accountTransaction.dto.response.TransactionGetResponse;
import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountTransactionService {
    TransactionDetailSearchResponse findByTransactionId(Long transactionId);
    Slice<TransactionGetResponse> showTransactionList(long accountid, LocalDateTime start, LocalDateTime end, String type, int page, int size);

}
