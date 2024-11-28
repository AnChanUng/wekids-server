package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.dto.enums.TransactionRequestType;
import com.wekids.backend.accountTransaction.dto.request.AccountTransactionListGetRequestParams;
import com.wekids.backend.baas.dto.request.AccountGetRequest;
import com.wekids.backend.baas.dto.request.AccountTransactionGetRequest;
import com.wekids.backend.accountTransaction.dto.response.*;
import com.wekids.backend.baas.dto.request.TransferRequest;
import com.wekids.backend.accountTransaction.dto.request.TransactionRequest;
import com.wekids.backend.accountTransaction.dto.request.UpdateMemoRequest;
import com.wekids.backend.accountTransaction.dto.response.TransactionDetailSearchResponse;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.baas.dto.response.AccountGetResponse;
import com.wekids.backend.baas.dto.response.AccountTransactionResponse;
import com.wekids.backend.baas.dto.response.TransferResponse;
import com.wekids.backend.baas.service.BaasService;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountTransactionServiceImpl implements AccountTransactionService {
    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountRepository accountRepository;
    private final BaasService baasService;

    @Override
    public TransactionDetailSearchResponse showTransaction(Long transactionId) {
        AccountTransaction accountTransaction = findAccountTransactionById(transactionId);
        return TransactionDetailSearchResponse.from(accountTransaction);

    }

    @Override
    @Transactional
    public void updateMemo(Long transactionId, UpdateMemoRequest request) {
        AccountTransaction accountTransaction = findAccountTransactionById(transactionId);
        accountTransaction.updateMemo(request.getMemo());
    }

    @Override
    @Transactional
    public void transfer(TransactionRequest transactionRequest) {
        Account parentAccount = findAccountByAccountNumber(transactionRequest.getParentAccountNumber());
        Account childAccount = findAccountByAccountNumber(transactionRequest.getChildAccountNumber());

        log.info("부모계좌번호 " + parentAccount);
        log.info("자식계좌번호 " + childAccount);

        updateAccount(parentAccount);
        updateAccount(childAccount);

        validateTransaction(transactionRequest, parentAccount, childAccount);

        TransferResponse transferResponse = transferBaaS(transactionRequest);
    }

    @Override
    @Transactional
    public TransactionHistoryResponse showTransactionList(Long accountId, AccountTransactionListGetRequestParams params) {
        Account account = findAccountByAccountId(accountId);
        LocalDate start = params.getStart();
        LocalDate end = params.getEnd();
        TransactionRequestType type = params.getType();
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize());

        if (pageable.getPageNumber() == 0) {
            updateAccount(account);

            LocalDateTime mostRecentDateTime = accountTransactionRepository
                    .findMostRecentTransactionDateTime(accountId, type, start.atStartOfDay(), end.atTime(LocalTime.MAX)).orElse(start.atStartOfDay());

            AccountTransactionGetRequest request = AccountTransactionGetRequest.of(
                    account.getAccountNumber(), mostRecentDateTime.plusSeconds(1), end.atTime(LocalTime.MAX), type.toString(), pageable);

            List<AccountTransactionResponse> accountTransactionGetResponses = baasService.getAccountTransactionList(request);

            accountTransactionGetResponses.forEach(accountTransactionGetResponse -> {
                accountTransactionRepository.save(AccountTransaction.of(account, accountTransactionGetResponse));
            });
        }

        Slice<TransactionResult> transactionResultSlice = accountTransactionRepository.findAccountTransactionByTypeSortedByTimeDesc(accountId, type, start.atStartOfDay(), end.atTime(LocalTime.MAX), pageable);

        return TransactionHistoryResponse.of(
                account.getBalance().longValue(),
                transactionResultSlice.hasNext(),
                transactionResultSlice.getContent());
    }

    private TransferResponse transferBaaS(TransactionRequest transactionRequest) {
        TransferRequest request = TransferRequest.of(
                transactionRequest.getParentAccountNumber(),
                transactionRequest.getChildAccountNumber(),
                transactionRequest.getAmount()
        );

        return baasService.transfer(request);
    }

    private Account findAccountByAccountId(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_ACTIVE, "계좌를 찾을 수 없습니다."));
    }

    private Account findAccountByAccountNumber(String accountNumber) {
        log.debug("Searching for account with account number: {}", accountNumber);  // 로그 추가
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND,
                        "회원 계좌번호: " + accountNumber));
    }

    private AccountTransaction findAccountTransactionById(Long transactionId) {
        return accountTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new WekidsException(ErrorCode.TRANSACTION_NOT_FOUND, "거래 아이디: " + transactionId));
    }

    private void validateTransaction(TransactionRequest transactionRequest, Account parentAccount, Account childAccount) {
        if (transactionRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new WekidsException(ErrorCode.INVALID_TRANSACTION_AMOUNT, " 거래하려는 금액 " + transactionRequest.getAmount());
        }

        if (parentAccount.getState() != AccountState.ACTIVE) {
            throw new WekidsException(ErrorCode.ACCOUNT_NOT_ACTIVE,
                    "부모 계좌 상태가 활성 상태가 아닙니다. 상태: " + parentAccount.getState());
        }
        if (childAccount.getState() != AccountState.ACTIVE) {
            throw new WekidsException(ErrorCode.ACCOUNT_NOT_ACTIVE,
                    "자식 계좌 상태가 활성 상태가 아닙니다. 상태: " + childAccount.getState());
        }

        if (parentAccount.getBalance().compareTo(transactionRequest.getAmount()) < 0) {
            throw new WekidsException(ErrorCode.INVALID_TRANSACTION_AMOUNT,
                    "부모의 잔액" + parentAccount.getBalance() + " 거래하려는 금액 " + transactionRequest.getAmount());
        }

        if (parentAccount.getAccountNumber() == null || childAccount.getAccountNumber() == null) {
            throw new WekidsException(ErrorCode.INVALID_ACCOUNT_NUMBER,
                    "부모 AccountNum:" + parentAccount + " 자식 AccountNum" + childAccount);
        }

        if (parentAccount.getAccountNumber().equals(childAccount.getAccountNumber())) {
            throw new WekidsException(ErrorCode.INVALID_ACCOUNT_NUMBER,
                    "부모 계좌와 자식 계좌가 동일할 수 없습니다.");
        }
    }

    private void updateAccount(Account account) {
        AccountGetResponse accountResponse = baasService.getAccount(AccountGetRequest.of(account.getAccountNumber()));

        if (!account.getState().equals(accountResponse.getState())) {
            account.updateState(accountResponse.getState());
        }

        account.updateBalance(BigDecimal.valueOf(accountResponse.getBalance()));
    }
}