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
import com.wekids.backend.accountTransaction.dto.result.TransactionListResult;
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
    public TransactionListResponse getTransactionList(long accountid, String start, String end, String type, int page, int size){
        // 기본은 5개씩 무한스크롤 반영?
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        //String의 start와 end를 날짜타입으로 변경
        LocalDateTime localDateStartTime;
        LocalDateTime localDateEndTime;
        try { // 값이 제대로 들어오지 않았을 경우
            localDateStartTime = LocalDateTime.parse(start, formatter);
            localDateEndTime = LocalDateTime.parse(end, formatter);
        } catch (DateTimeParseException e) {
            throw new WekidsException(ErrorCode.INVALID_DATE_FORMAT, "날짜 형식이 올바르지 않습니다. 형식은 yyyy-MM-dd'T'HH:mm:ss 여야 합니다.");
        }
        // 페이지 검색
        Pageable limit = PageRequest.of(page, size);
        Account account = findById(accountid);
        String account_owner = account.getMember().getName();
        TransactionType transactionType = TransactionType.valueOf(type);
        //Slice로 size개수만큼 추출
        Slice<AccountTransaction> accountTransactions = accountTransactionRepository.findSliceBySenderOrReceiverAndCreatedAtBetweenAndType(limit, account_owner, account_owner, localDateStartTime, localDateEndTime, transactionType);
        List<TransactionListResult> transactionListResultList = accountTransactions.stream()
                .map(TransactionListResult::new)
                .toList();
        return new TransactionListResponse(findById(accountid), transactionListResultList, accountTransactions.hasNext());



    }

    private Account findById(long id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "계좌를 찾을 수 없습니다."));
    }


}
