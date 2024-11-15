package com.wekids.backend.account.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.dto.response.AccountResponse;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    @Override
    public List<AccountResponse> getAccount() {
        //AccountRepo에서 계좌 조회
        List<Account> accountList = getAllAccounts();
        return accountList.stream().map(
                AccountResponse::new
        ).collect(Collectors.toList());
    }

    public List<Account> getAllAccounts() {
        return Optional.of(accountRepository.findAll())
                .filter(accounts -> !accounts.isEmpty())
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "계좌를 찾을 수 없습니다."));
    }
}


