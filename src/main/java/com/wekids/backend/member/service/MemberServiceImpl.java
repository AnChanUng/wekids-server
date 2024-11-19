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

    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final DesignRepository designRepository;

    @Override
    public ParentAccountResponse getParentAccount() {
        Long parentId = 1L;

        // 부모 개인 정보 조회
        Parent parent = (Parent)findMemberByMemberId(parentId);

        // 부모 계좌 정보 조회
        Account parentAccount = findAccountByMember(parent);

        // 부모 디자인 정보 조회
        Design design = findDesignByMemberId(parentId);

        ParentResponse parentResponse = ParentResponse.of(parent, parentAccount, design);
        // 자녀 정보 조회 및 설정
        List<Child> children = findChildrenByByParentId(parentId);
        List<ChildResponse> childResponse = children.stream().map(child -> {
            // 각 자녀의 계좌 정보 조회
            Account childAccount = findAccountByMember(child);
            Design childDesign = findDesignByMemberId(child.getId());
            return ChildResponse.of(child, childAccount, childDesign);
        }).collect(Collectors.toList());

        // ParentAccountResponse 객체 생성 및 부모 정보 설정
        return new ParentAccountResponse(parentResponse, childResponse);
    }

    public Member findMemberByMemberId(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "id가" + id + "인 정보를 찾을 수 없습니다."));
    }

    public Account findAccountByMember(Member member){
        return accountRepository.findByMember(member)
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "id가" + member.getId() + "인 사람의 계좌를 찾을 수 없습니다."));
    }

    public Design findDesignByMemberId(Long id){
        return designRepository.findByMemberId(id)
                .orElseThrow(() -> new WekidsException(ErrorCode.DESIGN_NOT_FOUND, "id가" + id + "의 디자인 정보를 찾을 수 없습니다."));
    }
    public List<Child> findChildrenByByParentId(Long id){
        return memberRepository.findChildrenByParentId(id)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "parentId가" + id + "인 자식들 정보를 찾을 수 없습니다."));
    }

}
