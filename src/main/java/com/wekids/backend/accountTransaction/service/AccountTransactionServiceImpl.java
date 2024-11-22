package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.dto.request.UpdateMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
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

        AccountTransaction accountTransaction = findAccountTransactionById(transactionId, "transaction id값");
        return TransactionDetailSearchResponse.from(accountTransaction);

    }

    @Override
    @Transactional
    public void saveMemo(Long transactionId, UpdateMemoRequest request) {
        AccountTransaction accountTransaction = findAccountTransactionById(transactionId, "memo업데이트를 하는 거래내역id");
        accountTransaction.updateMemo(request.getMemo());
    }


    private AccountTransaction findAccountTransactionById(Long transactionId, String message) {
        return accountTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new WekidsException(ErrorCode.TRANSACTION_NOT_FOUND, message + transactionId));
    }
}
