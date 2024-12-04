package com.wekids.backend.member.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.account.service.AccountService;
import com.wekids.backend.member.dto.response.ChildResponse;
import com.wekids.backend.member.repository.ChildRepository;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.repository.DesignRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChildServiceImpl implements ChildService {
    private final ChildRepository childRepository;
    private final AccountRepository accountRepository;
    private final DesignRepository designRepository;
    private final AccountService accountService;

    @Override
    @Transactional
    public ChildResponse getChildAccount(Long childId) {
        Child child = findChildById(childId);
        Account childAccount = findChildAccountByMember(child);
        Design design = findDesignById(childId);

        accountService.updateAccount(childAccount);

        return ChildResponse.of(child, childAccount, design);
    }

    private Child findChildById(Long childId) {
        return childRepository.findById(childId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "자식 계정 아이디 : " + childId));
    }

    private Account findChildAccountByMember(Child child) {
        return accountRepository.findByMember(child).orElse(null);
    }

    private Design findDesignById(Long childId) {
        return designRepository.findById(childId).orElse(null);
    }
}