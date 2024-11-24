package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.dto.enums.TransactionRequestType;
import com.wekids.backend.accountTransaction.dto.request.BaasTransactionRequest;
import com.wekids.backend.accountTransaction.dto.response.*;
import com.wekids.backend.accountTransaction.dto.request.UpdateMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.baas.aop.BassLogAndHandleException;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountTransactionServiceImpl implements AccountTransactionService {
    @Value("${baas.api.baas-url}")
    private String baasURL;
    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;


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
    @Transactional
    @BassLogAndHandleException
    public TransactionHistoryResponse showTransactionList(Long accountId, LocalDate start, LocalDate end, TransactionRequestType type, Pageable pageable){
        Account account = findAccountByAccountId(accountId);

        if(pageable.getPageNumber() == 0){
            LocalDateTime mostRecentDateTime = accountTransactionRepository
                    .findMostRecentTransactionDateTime(accountId, type, start.atStartOfDay(), end.atTime(LocalTime.MAX)).orElse(start.atStartOfDay());
            BaasTransactionRequest request = BaasTransactionRequest.of(
                    account.getAccountNumber(), mostRecentDateTime.toLocalDate(), end, type.toString(), pageable);

            List<BaasTransactionResponse> baasResponse = findBaasTransactionResponseByBaas(accountId, request);

            baasResponse.forEach(item -> {
                accountTransactionRepository.save(AccountTransaction.of(account, item));
            });
        }

        Slice<TransactionResult> transactionResultSlice = accountTransactionRepository.findAccountTransactionByTypeSortedByTimeDesc(accountId, type, start.atStartOfDay(), end.atTime(LocalTime.MAX), pageable);

        return TransactionHistoryResponse.of(
                account.getBalance().longValue(),
                transactionResultSlice.hasNext(),
                transactionResultSlice.getContent());
    }

    private List<BaasTransactionResponse> findBaasTransactionResponseByBaas(Long accountId, BaasTransactionRequest request){
        String url = baasURL + "/api/v1/getTransactions";

        ResponseEntity<List<BaasTransactionResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<List<BaasTransactionResponse>>() {}
        );

        if (response.getBody() == null) {
            throw new WekidsException(ErrorCode.BAAS_NOT_RESPONSE, accountId + "에 대한 정보가 baas의 db 상에 존재하지 않습니다.");
        }

        return response.getBody();
    }

    private Account findAccountByAccountId(long id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_ACTIVE, "계좌를 찾을 수 없습니다."));
    }
}