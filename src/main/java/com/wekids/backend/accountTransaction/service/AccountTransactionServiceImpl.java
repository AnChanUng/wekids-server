package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import com.wekids.backend.accountTransaction.dto.request.TransactionRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountRepository accountRepository;


    @Override
    public TransactionDetailSearchResponse findByTransactionId(Long transactionId) {

        AccountTransaction accountTransaction = accountTransactionById(transactionId);
        return TransactionDetailSearchResponse.from(accountTransaction);

    }

    @Override
    @Transactional
    public void saveTransaction(TransactionRequest transactionRequest) {
        Account parentAccount = getAccountById(transactionRequest.getParentAccountNumber());
        Account childAccount = getAccountById(transactionRequest.getChildAccountNumber());

        AccountTransaction parentTransaction = createParentAccountTransaction(transactionRequest, parentAccount);
        AccountTransaction childTransaction = createChildAccountTransaction(transactionRequest, childAccount);

        accountTransactionRepository.save(parentTransaction);
        accountTransactionRepository.save(childTransaction);
    }

    private Account getAccountById(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND,
                        "회원 id: " + accountNumber));
    }

    private AccountTransaction createParentAccountTransaction(TransactionRequest request, Account parentAccount) {
        BigDecimal newBalance = parentAccount.getBalance().subtract(request.getAmount());
        return createTransaction(request, TransactionType.WITHDRAWAL, newBalance);
    }

    private AccountTransaction createChildAccountTransaction(TransactionRequest request, Account childAccount) {
        BigDecimal newBalance = childAccount.getBalance().add(request.getAmount());
        return createTransaction(request, TransactionType.DEPOSIT, newBalance);
    }

    private AccountTransaction createTransaction(TransactionRequest request, TransactionType type, BigDecimal balance) {
        return AccountTransaction.builder()
                .title(request.getSender())
                .type(type)
                .amount(request.getAmount())
                .balance(balance)
                .sender(request.getSender())
                .receiver(request.getReceiver())
                .memo("") // Optional: 나중에 기본값이나 다른 설정 고려 가능
                .createdAt(LocalDateTime.now())
                .build();
    }

    private AccountTransaction accountTransactionById(Long transactionId) {
        return accountTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new WekidsException(ErrorCode.TRANSACTION_NOT_FOUND, "transactionId : " + transactionId));
    }
}
