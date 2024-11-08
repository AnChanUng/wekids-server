package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;

public interface AccountTransactionService {
    AccountTransaction findByTransactionId(Long transactionId);
}
