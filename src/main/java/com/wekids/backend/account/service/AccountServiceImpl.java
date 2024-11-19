package com.wekids.backend.account.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.dto.response.AccountChildResponse;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    @Override
    public List<AccountChildResponse> findChildrenAccountList(long parentId){
        List<Account> childrenAccountList = accountRepository.findAccountsByParentId(parentId);
        return childrenAccountList.stream().map(AccountChildResponse::from).toList();
    }
}
