package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import com.wekids.backend.accountTransaction.dto.request.BaaSTransferRequest;
import com.wekids.backend.accountTransaction.dto.request.TransactionRequest;
import com.wekids.backend.accountTransaction.dto.request.UpdateMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountTransactionServiceImpl implements AccountTransactionService {
    @Value("${baas.api.baas-url}")
    private String baasURL;
    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountRepository accountRepository;
    private final RestTemplate template;

    @Override
    public TransactionDetailSearchResponse showTransaction(Long transactionId) {
        AccountTransaction accountTransaction = findAccountTransactionById(transactionId, "transaction id값");
        return TransactionDetailSearchResponse.from(accountTransaction);

    }

    @Override
    @Transactional
    public void updateMemo(Long transactionId, UpdateMemoRequest request) {
        AccountTransaction accountTransaction = findAccountTransactionById(transactionId, "memo업데이트를 하는 거래내역id");
        accountTransaction.updateMemo(request.getMemo());
    }

    @Override
    @Transactional
    public void transfer(TransactionRequest transactionRequest) {
        Account parentAccount = findAccountByAccountNumber(transactionRequest.getParentAccountNumber());
        Account childAccount = findAccountByAccountNumber(transactionRequest.getChildAccountNumber());

        log.info("부모계좌번호 " + parentAccount);
        log.info("자식계좌번호 " + childAccount);

        validateTransaction(transactionRequest, parentAccount, childAccount);

        transferBaaS(transactionRequest);

        AccountTransaction parentTransaction = createParentAccountTransaction(transactionRequest, parentAccount);
        AccountTransaction childTransaction = createChildAccountTransaction(transactionRequest, childAccount);

        accountTransactionRepository.save(parentTransaction);
        accountTransactionRepository.save(childTransaction);
    }

    private void transferBaaS(TransactionRequest transactionRequest) {
        BaaSTransferRequest request = BaaSTransferRequest.of(
                transactionRequest.getParentAccountNumber(),
                transactionRequest.getChildAccountNumber(),
                transactionRequest.getAmount()
        );

        try {
            template.postForLocation(baasURL + "/api/v1/transactions", request);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            String errorMessage = "BaaS 요청 실패: " + e.getMessage();
            throw new WekidsException(ErrorCode.BAAS_REQUEST_FAILED, errorMessage);
        } catch (RestClientException e) {
            String errorMessage = "BaaS 요청 실패: 네트워크 오류 또는 알 수 없는 문제 - " + e.getMessage();
            throw new WekidsException(ErrorCode.BAAS_REQUEST_FAILED, errorMessage);
        }
    }


    private Account findAccountByAccountNumber(String accountNumber) {
        log.debug("Searching for account with account number: {}", accountNumber);  // 로그 추가
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND,
                        "회원 계좌번호: " + accountNumber));
    }

    private AccountTransaction createParentAccountTransaction(TransactionRequest request, Account parentAccount) {
        BigDecimal newBalance = parentAccount.getBalance().subtract(request.getAmount());
        parentAccount.updateAccountAmount(newBalance);
        return AccountTransaction.createTransaction(request, TransactionType.WITHDRAWAL, newBalance, parentAccount);
    }

    private AccountTransaction createChildAccountTransaction(TransactionRequest request, Account childAccount) {
        BigDecimal newBalance = childAccount.getBalance().add(request.getAmount());
        childAccount.updateAccountAmount(newBalance);
        return AccountTransaction.createTransaction(request, TransactionType.DEPOSIT, newBalance, childAccount);
    }


    private AccountTransaction findAccountTransactionById(Long transactionId, String message) {
        return accountTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new WekidsException(ErrorCode.TRANSACTION_NOT_FOUND, message + transactionId));
    }

    private void validateTransaction(TransactionRequest transactionRequest, Account parentAccount, Account childAccount) {
        if (transactionRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new WekidsException(ErrorCode.INVALID_TRANSACTION_AMOUNT, " 거래하려는 금액 " + transactionRequest.getAmount());
        }

        if (parentAccount.getBalance().compareTo(transactionRequest.getAmount()) < 0) {
            throw new WekidsException(ErrorCode.INVALID_TRANSACTION_AMOUNT,
                    "부모의 잔액" + parentAccount.getBalance() + " 거래하려는 금액 " + transactionRequest.getAmount());
        }

        if (parentAccount.getAccountNumber() == null || childAccount.getAccountNumber() == null) {
            throw new WekidsException(ErrorCode.INVALID_ACCOUNT_NUMBER,
                    "부모 AccountNum:" + parentAccount + " 자식 AccountNum" + childAccount);
        }

        if (parentAccount.getState() != AccountState.ACTIVE) {
            throw new WekidsException(ErrorCode.ACCOUNT_NOT_ACTIVE,
                    "부모 계좌 상태가 활성 상태가 아닙니다. 상태: " + parentAccount.getState());
        }
        if (childAccount.getState() != AccountState.ACTIVE) {
            throw new WekidsException(ErrorCode.ACCOUNT_NOT_ACTIVE,
                    "자식 계좌 상태가 활성 상태가 아닙니다. 상태: " + childAccount.getState());
        }
        if (parentAccount.getAccountNumber().equals(childAccount.getAccountNumber())) {
            throw new WekidsException(ErrorCode.INVALID_ACCOUNT_NUMBER,
                    "부모 계좌와 자식 계좌가 동일할 수 없습니다.");
        }
    }
}

