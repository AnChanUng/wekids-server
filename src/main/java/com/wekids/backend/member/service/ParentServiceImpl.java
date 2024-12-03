package com.wekids.backend.member.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.baas.dto.request.AccountGetRequest;
import com.wekids.backend.baas.dto.request.BankMemberIdGetRequest;
import com.wekids.backend.baas.dto.request.WekidsRegistrationRequest;
import com.wekids.backend.baas.dto.response.AccountGetResponse;
import com.wekids.backend.baas.dto.response.BankMemberIdResponse;
import com.wekids.backend.baas.service.BaasService;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.repository.DesignRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.enums.MemberState;
import com.wekids.backend.member.dto.request.AccountInquiryAgreeRequest;
import com.wekids.backend.member.dto.request.ParentAccountRegistrationRequest;
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
    private final BaasService baasService;

    @Override
    public ParentAccountResponse showParentAccount(Long parentId) {
        Parent parent = getParent(parentId);
        Account parentAccount = findAccountByMember(parent);
        Design design = findDesignByMemberId(parentId);
        ParentResponse parentResponse = ParentResponse.of(parent, parentAccount, design);
        List<ChildResponse> childResponses = showChildAccount(parentId);

        return ParentAccountResponse.of(parentResponse, childResponses);
    }

    @Override
    @Transactional
    public void agreeAccountInquiry(AccountInquiryAgreeRequest accountInquiryAgreeRequest, Long memberId) {
        Parent parent = getParent(memberId);

        WekidsRegistrationRequest wekidsRegistrationRequest = WekidsRegistrationRequest.of(parent.getName(), parent.getBirthday(), accountInquiryAgreeRequest.getResidentRegistrationNumber());

        BankMemberIdResponse response = baasService.registerWekids(wekidsRegistrationRequest);

        parent.saveBankMemberId(response.getBankMemberId());
    }

    @Override
    @Transactional
    public void registerParentAccount(ParentAccountRegistrationRequest request, Long memberId) {
        Parent parent = getParent(memberId);

        AccountGetRequest accountGetRequest = AccountGetRequest.of(request.getAccountNumber());
        AccountGetResponse accountGetResponse = baasService.getAccount(accountGetRequest);

        Account account = Account.of(accountGetResponse, parent);

        accountRepository.save(account);
    }

    private Parent getParent(Long memberId) {
        return parentRepository.findById(memberId).orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "회원 아이디: " + memberId));
    }

    private List<ChildResponse> showChildAccount(Long parentId) {
        return childRepository.findChildrenByParentId(parentId).stream()
                .filter(child -> child.getState().equals(MemberState.ACTIVE))
                .map(child -> {
                    Account childAccount = findAccountByMember(child);
                    Design childDesign = findDesignByMemberId(child.getId());
                    return ChildResponse.of(child, childAccount, childDesign);
                })
                .collect(Collectors.toList());
    }

    private Account findAccountByMember(Member member) {
        return accountRepository.findByMember(member).orElse(null);
    }

    private Design findDesignByMemberId(Long parentId) {
        return designRepository.findById(parentId).orElse(null);
    }
}
