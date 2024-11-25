package com.wekids.backend.account.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.dto.response.AccountChildResponse;
import com.wekids.backend.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Override
    public List<AccountChildResponse> showChildrenAccountList(Long parentId){
        List<Account> childrenAccountList = accountRepository.findAccountsByParentId(parentId);
        return childrenAccountList.stream().map(AccountChildResponse::from).toList();
    }
}
