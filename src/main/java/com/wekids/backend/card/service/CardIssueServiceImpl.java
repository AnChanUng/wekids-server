package com.wekids.backend.card.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.alarm.domain.Alarm;
import com.wekids.backend.alarm.repository.AlarmRepository;
import com.wekids.backend.baas.dto.request.AccountCreateRequest;
import com.wekids.backend.baas.dto.request.CardCreateRequest;
import com.wekids.backend.baas.dto.request.WekidsRegistrationRequest;
import com.wekids.backend.baas.dto.response.AccountCreateResponse;
import com.wekids.backend.baas.dto.response.BankMemberIdResponse;
import com.wekids.backend.baas.dto.response.CardCreateResponse;
import com.wekids.backend.baas.service.BaasService;
import com.wekids.backend.card.dto.request.IssueRequest;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.card.repository.CardRepository;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.repository.DesignRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.enums.CardState;
import com.wekids.backend.member.repository.ChildRepository;
import com.wekids.backend.member.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CardIssueServiceImpl implements CardIssueService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;
    private final DesignRepository designRepository;
    private final AlarmRepository alarmRepository;
    private final BaasService baasService;

    @Override
    @Transactional
    public void issueAccountAndCard(IssueRequest issueRequest, Long parentId) {
        Parent parent = getParent(parentId);
        Child child = getChild(issueRequest.getChildId());
        Design design = getDesign(child.getId());

        String simplePassword = parent.getSimplePassword();
        String accountPassword = issueRequest.getAccountPassword();
        String cardPassword = issueRequest.getCardPassword();
        String residentRegistrationNumber = issueRequest.getResidentRegistrationNumber();

        Long bankMemberId = createBankMember(child, residentRegistrationNumber, simplePassword);

        Account account = createAccount(bankMemberId, accountPassword, child, design);

        createCard(account, child, cardPassword, design);

        validateCardState(child.getCardState());
        child.updateCardState(CardState.CREATED);

        Alarm alarm = Alarm.createCardCreatedAlarm(child);
        alarmRepository.save(alarm);
    }

    private Parent getParent(Long parentId) {
        return parentRepository.findById(parentId).orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "부모 회원 아이디: " + parentId));
    }

    private Card createCard(Account account, Child child, String cardPassword, Design design) {
        CardCreateRequest cardCreateRequest = CardCreateRequest.of(account.getAccountNumber(), child.getBankMemberId(), cardPassword);
        CardCreateResponse cardCreateResponse = baasService.createCard(cardCreateRequest);

        String cardName = createCardName(account.getMember().getName());
        Card card = Card.of(cardCreateResponse, cardCreateRequest.getPassword(), account, cardName);
        cardRepository.save(card);

        design.updateCard(card);

        return card;
    }

    private Account createAccount(Long bankMemberId, String accountPassword, Child child, Design design) {
        AccountCreateRequest accountBaasRequest = AccountCreateRequest.of(bankMemberId, accountPassword);
        AccountCreateResponse accountCreateResponse = baasService.createAccount(accountBaasRequest);

        Account account = Account.of(accountCreateResponse.getAccountNumber(), accountPassword, child);
        accountRepository.save(account);

        design.updateAccount(account);

        return account;
    }

    private Long createBankMember(Child child, String residentRegistrationNumber, String simplePassword) {
        WekidsRegistrationRequest wekidsRegistrationRequest = WekidsRegistrationRequest.of(child.getName(), child.getBirthday(), residentRegistrationNumber);
        BankMemberIdResponse bankMemberIdResponse = baasService.registerWekids(wekidsRegistrationRequest);
        Long bankMemberId = bankMemberIdResponse.getBankMemberId();

        child.saveBankMemberId(bankMemberId);
        child.saveSimplePassword(simplePassword);

        return bankMemberId;
    }

    private void validateCardState(CardState cardState) {
        if (!cardState.equals(CardState.READY))
            throw new WekidsException(ErrorCode.INVALID_CARD_STATE, "현재 카드 상태: " + cardState + "변경하려는 카드 상태: " + CardState.CREATED);
    }

    private Design getDesign(Long memberId) {
        return designRepository.findById(memberId).orElseThrow(() -> new WekidsException(ErrorCode.DESIGN_NOT_FOUND, "회원 아이디: " + memberId));
    }

    private String createCardName(String name) {
        return name.substring(name.length() - 2) + "핑";
    }


    private Child getChild(Long memberId) {
        return childRepository.findById(memberId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, String.format("회원 아이디: %s", memberId)));
    }
}