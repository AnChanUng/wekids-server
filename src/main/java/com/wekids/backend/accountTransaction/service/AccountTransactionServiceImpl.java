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
import java.util.List;
import java.util.Objects;

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
    public List<TransactionGetResponse> showTransactionList(long accountid, LocalDateTime start, LocalDateTime end, String type, int page, int size) {

        String url = "http://localhost:9090/api/v1/getTransactions";
        TransactionGetRequest transactionGetRequest = TransactionGetRequest.builder()
                .accountNumber("111")
                .build();
        System.out.println(transactionGetRequest);

        try {
            // 요청 전송
            HttpEntity<TransactionGetRequest> requestEntity = new HttpEntity<>(transactionGetRequest);
            ResponseEntity<TransactionGetResponse[]> responseEntity = restTemplate.postForEntity(
                    url, requestEntity, TransactionGetResponse[].class);

            // 응답 처리
            if (responseEntity.getBody() != null) {
                return Arrays.asList(responseEntity.getBody());
            } else {
                throw new WekidsException(ErrorCode.TRANSACTION_NOT_FOUND, accountid + "에 대한 정보가 db 상에 존재하지 않습니다.")
            }
        } catch (Exception ex) {
            throw new WekidsException(ErrorCode.INVALID_TRANSACTION, accountid + "의 BaaS 서버 접근이 잘못 되었습니다.");
        }

    }

    private Account findByAccountId(long id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "계좌를 찾을 수 없습니다."));
    }


}
