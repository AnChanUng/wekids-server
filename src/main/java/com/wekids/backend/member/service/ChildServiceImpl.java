package com.wekids.backend.member.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.member.dto.response.ChildResponse;
import com.wekids.backend.member.repository.ChildRepository;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.repository.DesignRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public ChildResponse getChildAccount() {
        Long childId = 2L;

        Child child = findChildById(childId);
        Account childAccount = findChildAccountByMember(child);
        Design design = findDesignById(childId);

        return ChildResponse.from(child, childAccount, design);
    }

    private Child findChildById(Long childId) {
        return childRepository.findById(childId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "자식 계정 아이디 : " + childId));
    }

    private Account findChildAccountByMember(Child child) {
        return accountRepository.findByMember(child)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "계정 정보를 찾을 수 없습니다." ));
    }

    private Design findDesignById(Long childId) {
        return designRepository.findById(childId).orElseThrow(() -> new WekidsException(ErrorCode.DESIGN_NOT_FOUND, "자식 계좌를 찾을 수 없습니다." + childId));
    }
}