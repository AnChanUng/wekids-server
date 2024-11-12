package com.wekids.backend.child.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.child.dto.response.ChildAccountResponse;
import com.wekids.backend.child.dto.result.ChildAccountResult;
import com.wekids.backend.child.repository.ChildRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ChildServiceImpl implements ChildService {
    @Autowired
    ChildRepository childRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public ChildAccountResponse getChildAccount() {
        Long childId = 1L;

        // 자식 개인 정보 조회
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "자식 정보를 찾을 수 없습니다."));

        // 자식 계좌 정보 조회
        Account childAccount = accountRepository.findByMember(child)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "자식 계좌를 찾을 수 없습니다."));

        // 자식 정보 및 자식 계좌 정보를 설정
        List<ChildAccountResult> accounts = childRepository.findChildrenByParentId(childId).stream()
                .map(c -> new ChildAccountResult(childAccount, c))
                .collect(Collectors.toList());

        return new ChildAccountResponse(childAccount, child, accounts);

    }

}
