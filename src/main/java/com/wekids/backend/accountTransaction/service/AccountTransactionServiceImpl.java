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
        // 기본은 5개씩 무한스크롤 반영?
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        //String의 start와 end를 날짜타입으로 변경

        // 페이지 검색
        Pageable limit = PageRequest.of(page, size);
        Account account = findByAccountId(accountid);
        String account_owner = account.getMember().getName();
        Slice<AccountTransaction> transactionList = createTransactionList(limit, account_owner, start, end, type);
        List<TransactionItemResponse> transactionListResultList = transactionList
                .stream()
                .map(TransactionItemResponse::from)
                .toList();
        return TransactionListResponse.from(findByAccountId(accountid), transactionListResultList, transactionList.hasNext());




    }

    private Account findByAccountId(long id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "계좌를 찾을 수 없습니다."));
    }

    private Slice<AccountTransaction> createTransactionList(Pageable limit, String account_owner, LocalDateTime start, LocalDateTime end, String type){
        Slice<AccountTransaction> accountTransactions;

        // "ALL"인 경우와 특정 type인 경우에 따라 다른 쿼리 수행
        if ("ALL".equals(type)) {
            accountTransactions = accountTransactionRepository.findSliceBySenderOrReceiverAndCreatedAtBetween(
                    limit, account_owner, account_owner, start, end);
        } else {
            TransactionType transactionType = TransactionType.valueOf(type);
            accountTransactions = accountTransactionRepository.findSliceBySenderOrReceiverAndCreatedAtBetweenAndType(
                    limit, account_owner, account_owner, start, end, transactionType);
        }

        // TransactionListResult 목록을 생성
        List<TransactionItemResponse> transactionListResultList = accountTransactions.stream()
                .map(TransactionItemResponse::from)
                .toList();

        // TransactionListResponse 객체를 생성하여 반환
        return accountTransactions;
    }


}
