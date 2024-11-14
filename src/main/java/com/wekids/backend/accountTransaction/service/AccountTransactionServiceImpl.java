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
        LocalDateTime localDateStartTime = LocalDateTime.parse(start, formatter);
        LocalDateTime localDateEndTime = LocalDateTime.parse(end, formatter);
        boolean hasNext = false;
        Pageable limit = PageRequest.of(page, size);
        Account account = accountRepository.findById(accountid)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "계좌를 찾을 수 없습니다."));
        String account_owner = account.getMember().getName();
        TransactionType transactionType = TransactionType.valueOf(type);


        return null;



    }
}
