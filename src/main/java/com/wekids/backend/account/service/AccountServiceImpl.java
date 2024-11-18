package com.wekids.backend.account.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.dto.response.AccountChildListResponse;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.design.domain.Design;
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
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MemberRepository memberRepository;

    @Override
    public List<AccountChildListResponse> findAccountChildList(long parentId){
        List<Child> children = memberRepository.findChildrenByParentId(parentId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, parentId + "의 자식들 정보를 찾을 수 없습니다."));// parent_child 테이블을 통해 자녀 목록 조회
        // 각 자녀의 계좌 정보 조회
        return children.stream().map(child -> {
            // 각 자녀의 계좌 정보 조회
            Account childAccount = accountRepository.findByMember(child)
                    .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "자식" + child.getId() + "의 계좌를 찾을 수 없습니다."));
            return AccountChildListResponse.from(childAccount);
        }).toList();
    }
}
