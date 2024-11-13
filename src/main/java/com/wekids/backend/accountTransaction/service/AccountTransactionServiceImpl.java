package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountTransactionRepository accountTransactionRepository;


    @Override
    public TransactionDetailSearchResponse findByTransactionId(Long transactionId) {
        return null;
    }
}
