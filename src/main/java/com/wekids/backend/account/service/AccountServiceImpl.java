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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    @Override
    public AccountResponse getAccount(Long account_id) {

        return null;
    }
}
