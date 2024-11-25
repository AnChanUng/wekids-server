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
import com.wekids.backend.member.repository.ChildRepository;
import com.wekids.backend.member.repository.ParentRepository;
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
public class ParentServiceImpl implements ParentService {
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final AccountRepository accountRepository;
    private final DesignRepository designRepository;

    public ParentAccountResponse showParentAccount(Long parentId) {
        Parent parent = findParentByMemberId(parentId);
        Account parentAccount = findAccountByMember(parent);
        Design design = findDesignByMemberId(parentId);
        ParentResponse parentResponse = ParentResponse.of(parent, parentAccount, design);
        List<ChildResponse> childResponses = showChildAccount(parentId);

        return ParentAccountResponse.of(parentResponse, childResponses);
    }

    private List<ChildResponse> showChildAccount(Long parentId){
        return childRepository.findChildrenByParentId(parentId).stream()
                .filter(child -> child.getState().equals(MemberState.ACTIVE))
                .map(child -> {
                    Account childAccount = findAccountByMember(child);
                    Design childDesign = findDesignByMemberId(child.getId());
                    return ChildResponse.of(child, childAccount, childDesign);
                })
                .collect(Collectors.toList());
    }

    private Parent findParentByMemberId(Long parentId){
        return parentRepository.findById(parentId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "parentId가 " + parentId + "인 정보를 찾을 수 없습니다."));
    }

    private Account findAccountByMember(Member member){
        return accountRepository.findByMember(member).orElse(null);
    }

    private Design findDesignByMemberId(Long parentId){
        return designRepository.findById(parentId).orElse(null);
    }
}
