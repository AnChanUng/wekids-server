package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;
import com.wekids.backend.accountTransaction.dto.response.TransactionItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

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


    private AccountTransaction accountTransactionById(Long transactionId) {
        return accountTransactionRepository.findById(transactionId).orElseThrow(() -> {
            throw new WekidsException(ErrorCode.TRANSACTION_NOT_FOUND, "transactionId : " + transactionId);
        });
    }



    @Override
    public TransactionListResponse showTransactionList(long accountid, LocalDateTime start, LocalDateTime end, String type, int page, int size){

        Pageable limit = PageRequest.of(page, size);
        Account account = findByAccountId(accountid);
        String owner = account.getMember().getName();
        Slice<AccountTransaction> transactionList = createTransactionList(limit, owner, start, end, type);
        List<TransactionItemResponse> transactionListResultList = transactionList
                .stream()
                .map(TransactionItemResponse::from)
                .toList();
        return TransactionListResponse.from(findByAccountId(accountid).getBalance(), transactionListResultList, transactionList.hasNext());

    }

    private Account findByAccountId(long id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "계좌를 찾을 수 없습니다."));
    }

    private Slice<AccountTransaction> createTransactionList(Pageable limit, String owner, LocalDateTime start, LocalDateTime end, String type){
        Slice<AccountTransaction> accountTransactions;

        // "ALL"인 경우와 특정 type인 경우에 따라 다른 쿼리 수행
        if ("ALL".equals(type)) {
            accountTransactions = accountTransactionRepository.findSliceBySenderOrReceiverAndCreatedAtBetween(
                    limit, owner, owner, start, end);
        } else {
            TransactionType transactionType = TransactionType.valueOf(type);
            accountTransactions = accountTransactionRepository.findSliceBySenderOrReceiverAndCreatedAtBetweenAndType(
                    limit, owner, owner, start, end, transactionType);
        }

        return accountTransactions;
    }


}
