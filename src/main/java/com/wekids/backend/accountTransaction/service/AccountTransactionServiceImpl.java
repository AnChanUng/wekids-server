package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;
import com.wekids.backend.accountTransaction.dto.result.TransactionListResult;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountTransactionServiceImpl implements AccountTransactionService{

    private final AccountTransactionRepository accountTransactionRepository;

    private final AccountRepository accountRepository;

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
        
        boolean hasNext = false;
        // 페이지 검색
        Pageable limit = PageRequest.of(page, size);
        Account account = accountRepository.findById(accountid)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "계좌를 찾을 수 없습니다."));
        String account_owner = account.getMember().getName();
        TransactionType transactionType = TransactionType.valueOf(type);
        // 추후에 시간나면 더 좋은 방법을 한번 고민해보자
        Long count = accountTransactionRepository.countBySenderOrReceiverAndCreatedAtBetweenAndType(account_owner, account_owner, localDateStartTime, localDateEndTime, transactionType);
        List<AccountTransaction> accountTransactions = accountTransactionRepository.findBySenderOrReceiverAndCreatedAtBetweenAndType(account_owner, account_owner, localDateStartTime, localDateEndTime, transactionType, limit);
        // 총 개수가 12개 이고 size가 5이고 page가 0이라고 하면 2 > 1
        // 총 개수가 12개 이고 szie가 5이고 page가 1이라고 하면 2 = 2
        // 총 개수가 12개 이고 size가 5이고 page가 2이라고 하면 2 < 3
       if((count / size) > page){
           hasNext = true;
       }
        List<TransactionListResult> transactionListResultList = accountTransactions.stream()
                .map(TransactionListResult::new)
                .toList();

        return new TransactionListResponse(account, transactionListResultList, hasNext);



    }
}
