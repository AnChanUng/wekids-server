package com.wekids.backend.member.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.dto.request.ParentAccountRequest;
import com.wekids.backend.member.dto.response.ParentAccountResponse;
import com.wekids.backend.member.dto.result.ParentAccountResult;
import com.wekids.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public ParentAccountResponse getParent_Account(ParentAccountRequest parentAccountRequest) {
        Long parentId = parentAccountRequest.getParentId();
        
        // 부모 개인 정보 조회
        Parent parent = memberRepository.findByIdAndMemberType(parentId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "부모 정보를 찾을 수 없습니다."));

        // 부모 계좌 정보 조회
        Account parentAccount = accountRepository.findByMember(parent)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "부모 계좌를 찾을 수 없습니다."));

        // ParentAccountResponse 객체 생성 및 부모 정보 설정
        ParentAccountResponse response = new ParentAccountResponse();
        response.setName(parent.getName());
        response.setAccountNumber(parentAccount.getAccountNumber());
        response.setProfile(parent.getProfile());
        response.setBalance(parentAccount.getAmount());
        response.setDesignType(parentAccount.getDesignType());
        response.setAccountId(parentAccount.getId());

        // 자녀 정보 조회 및 설정
        List<Child> children = memberRepository.findChildrenByParentId(parentId); // parent_child 테이블을 통해 자녀 목록 조회
        List<ParentAccountResult> childResults = children.stream().map(child -> {
            // 각 자녀의 계좌 정보 조회
            Account childAccount = accountRepository.findByMember(child)
                    .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "자식 계좌를 찾을 수 없습니다."));

            ParentAccountResult childResult = new ParentAccountResult();
            childResult.setChildId(child.getId());
            childResult.setName(child.getName());
            childResult.setAccountNumber(childAccount.getAccountNumber());
            childResult.setProfile(child.getProfile());
            childResult.setBalance(childAccount.getAmount());
            childResult.setDesignType(childAccount.getDesignType());
            childResult.setAccountId(childAccount.getId());

            return childResult;
        }).collect(Collectors.toList());

        response.setChildren(childResults);
        return response;
    }

}
