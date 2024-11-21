package com.wekids.backend.member.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.repository.DesignRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.enums.MemberState;
import com.wekids.backend.member.dto.response.ChildResponse;
import com.wekids.backend.member.dto.response.ParentAccountResponse;
import com.wekids.backend.member.dto.response.ParentResponse;
import com.wekids.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final DesignRepository designRepository;

    public ParentAccountResponse showParentAccount(Long parentId) {
        Parent parent = (Parent)findMemberByMemberId(parentId);
        Account parentAccount = findAccountByMember(parent);
        Design design = findDesignByMemberId(parentId);
        ParentResponse parentResponse = ParentResponse.of(parent, parentAccount, design);
        List<ChildResponse> childResponses = showChildAccount(parentId);

        return new ParentAccountResponse(parentResponse, childResponses);
    }

    private List<ChildResponse> showChildAccount(Long parentId){
        return findChildrenByByParentId(parentId).stream()
                .filter(child -> child.getState().equals(MemberState.ACTIVE))
                .map(child -> {
                    Account childAccount = findAccountByMember(child);
                    Design childDesign = findDesignByMemberId(child.getId());
                    return ChildResponse.of(child, childAccount, childDesign);
                })
                .collect(Collectors.toList());
    }

    private Member findMemberByMemberId(Long parentId){
        return memberRepository.findById(parentId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "id가" + parentId + "인 정보를 찾을 수 없습니다."));
    }

    private Account findAccountByMember(Member member){
        return accountRepository.findByMember(member).orElse(null);
    }

    private Design findDesignByMemberId(Long parentId){
        return designRepository.findByMemberId(parentId).orElse(null);
    }
    private List<Child> findChildrenByByParentId(Long parentId){
        return memberRepository.findChildrenByParentId(parentId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "parentId가" + parentId + "인 자식들 정보를 찾을 수 없습니다."));
    }
}
