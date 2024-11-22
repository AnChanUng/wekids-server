package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.dto.response.*;
import com.wekids.backend.accountTransaction.dto.request.UpdateMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
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

        AccountTransaction accountTransaction = findAccountTransactionById(transactionId, "transaction id값");
        return TransactionDetailSearchResponse.from(accountTransaction);

    }

    private AccountTransaction accountTransactionById(Long transactionId) {
        return accountTransactionRepository.findById(transactionId).orElseThrow(() -> {
            throw new WekidsException(ErrorCode.TRANSACTION_NOT_FOUND, "transactionId : " + transactionId);
        });
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


    @Override
    public Slice<TransactionGetResponse> showTransactionList(long accountid, LocalDateTime start, LocalDateTime end, String type, int page, int size) {

        Account account = findByAccountId(accountid);


        String url = "http://localhost:9090/api/v1/getTransactions";
        TransactionGetRequest transactionGetRequest = TransactionGetRequest.builder()
                .accountNumber(account.getAccountNumber())
                .start(start != null ? start.toLocalDate() : null)
                .end(end != null ? end.toLocalDate() : null)
                .type(type)
                .page(page)
                .size(size)
                .build();

        try {
            HttpEntity<TransactionGetRequest> requestEntity = new HttpEntity<>(transactionGetRequest);
            ResponseEntity<TransactionGetResponse[]> responseEntity = restTemplate.postForEntity(
                    url, requestEntity, TransactionGetResponse[].class);

            if (responseEntity.getBody() != null) {
                List<TransactionGetResponse> allTransactions = Arrays.asList(responseEntity.getBody());

                List<TransactionGetResponse> filteredTransactions = type.equals("ALL")
                        ? allTransactions
                        : allTransactions.stream()
                        .filter(tx -> tx.getType().equalsIgnoreCase(type))
                        .collect(Collectors.toList());

                Pageable pageable = PageRequest.of(page, size);

                int fromIndex = (int) pageable.getOffset();
                int toIndex = Math.min(fromIndex + pageable.getPageSize(), filteredTransactions.size());

                if (fromIndex >= filteredTransactions.size()) {
                    return new SliceImpl<>(Collections.emptyList(), pageable, false);
                }

                boolean hasNext = toIndex < filteredTransactions.size();
                return new SliceImpl<>(filteredTransactions.subList(fromIndex, toIndex), pageable, hasNext);
            } else {
                throw new WekidsException(ErrorCode.TRANSACTION_NOT_FOUND, accountid + "에 대한 정보가 db 상에 존재하지 않습니다.");
            }
        } catch (Exception ex) {
            throw new WekidsException(ErrorCode.INVALID_TRANSACTION, accountid + "의 BaaS 서버 접근이 잘못되었습니다.");
        }
    }

    public Account findByAccountId(long id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "계좌를 찾을 수 없습니다."));
    }

}
