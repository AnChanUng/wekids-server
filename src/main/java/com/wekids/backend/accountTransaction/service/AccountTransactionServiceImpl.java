package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.dto.response.*;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountTransactionRepository accountTransactionRepository;

    private final AccountRepository accountRepository;

    private final RestTemplate restTemplate = new RestTemplate();


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
    public Slice<TransactionGetResponse> showTransactionList(long accountid, LocalDateTime start, LocalDateTime end, String type, int page, int size) {

        String url = "http://localhost:9090/api/v1/getTransactions";
        TransactionGetRequest transactionGetRequest = TransactionGetRequest.builder()
                .accountNumber("111") // accountId를 문자열로 변환
                .start(start != null ? start.toLocalDate() : null)
                .end(end != null ? end.toLocalDate() : null)
                .type(type)
                .page(page)
                .size(size)
                .build();

        try {
            // 요청 전송
            HttpEntity<TransactionGetRequest> requestEntity = new HttpEntity<>(transactionGetRequest);
            ResponseEntity<TransactionGetResponse[]> responseEntity = restTemplate.postForEntity(
                    url, requestEntity, TransactionGetResponse[].class);

            if (responseEntity.getBody() != null) {
                List<TransactionGetResponse> allTransactions = Arrays.asList(responseEntity.getBody());

                // 타입 필터링
                List<TransactionGetResponse> filteredTransactions = type.equals("ALL")
                        ? allTransactions
                        : allTransactions.stream()
                        .filter(tx -> tx.getType().equalsIgnoreCase(type))
                        .collect(Collectors.toList());

                // Pageable 생성
                Pageable pageable = PageRequest.of(page, size);

                // 페이징 처리 (Slice 생성)
                int fromIndex = (int) pageable.getOffset();
                int toIndex = Math.min(fromIndex + pageable.getPageSize(), filteredTransactions.size());

                if (fromIndex >= filteredTransactions.size()) {
                    return new SliceImpl<>(Collections.emptyList(), pageable, false); // 빈 Slice 반환
                }

                boolean hasNext = toIndex < filteredTransactions.size();
                return new SliceImpl<>(filteredTransactions.subList(fromIndex, toIndex), pageable, hasNext); // Slice 반환
            } else {
                throw new WekidsException(ErrorCode.TRANSACTION_NOT_FOUND, accountid + "에 대한 정보가 db 상에 존재하지 않습니다.");
            }
        } catch (Exception ex) {
            throw new WekidsException(ErrorCode.INVALID_TRANSACTION, accountid + "의 BaaS 서버 접근이 잘못되었습니다.");
        }

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
