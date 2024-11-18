package com.wekids.backend.member.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.dto.response.ChildResponse;
import com.wekids.backend.member.dto.response.ParentAccountResponse;
import com.wekids.backend.member.dto.response.ParentResponse;
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
public class MemberServiceImpl implements MemberService{
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public ParentAccountResponse getParentAccount() {
        Long parentId = 1L;

        // 부모 개인 정보 조회
        Parent parent = memberRepository.findByIdAndMemberType(parentId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "parentId가" + parentId + "인 부모 정보를 찾을 수 없습니다."));

        // 부모 계좌 정보 조회
        Account parentAccount = accountRepository.findByMember(parent)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "id가" + parent.getId() + "부모 계좌를 찾을 수 없습니다."));

        // 부모 디자인 정보 조회
        Design design = accountRepository.findDesignByAccountId(parentId);

        ParentResponse parentResponse = ParentResponse.from(parent, parentAccount, design);
        // 자녀 정보 조회 및 설정
        List<Child> children = memberRepository.findChildrenByParentId(parentId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "parentId가" + parentId + "인 자식들 정보를 찾을 수 없습니다.")); // parent_child 테이블을 통해 자녀 목록 조회
        List<ChildResponse> childResponse = children.stream().map(child -> {
            // 각 자녀의 계좌 정보 조회
            Account childAccount = accountRepository.findByMember(child)
                    .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "id가" + child.getId() + "자식 계좌를 찾을 수 없습니다."));
            Design childDesign = accountRepository.findDesignByAccountId(child.getId());
            return ChildResponse.from(child, childAccount, childDesign);
        }).collect(Collectors.toList());

        // ParentAccountResponse 객체 생성 및 부모 정보 설정
        return new ParentAccountResponse(parentResponse, childResponse);
    }

}
