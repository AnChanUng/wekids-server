package com.wekids.backend.card.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.card.dto.request.AccountBaasRequest;
import com.wekids.backend.card.dto.request.CardBaasRequest;
import com.wekids.backend.card.dto.request.MemberBaasRequest;
import com.wekids.backend.card.dto.request.IssueRequest;
import com.wekids.backend.card.dto.response.AccountBaasResponse;
import com.wekids.backend.card.dto.response.CardBaasResponse;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.card.dto.response.MemberBaasResponse;
import com.wekids.backend.card.repository.CardRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.repository.ChildRepository;
import com.wekids.backend.member.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CardIssueServiceImpl implements CardIssueService {
    private static final String BAAS_URL = "http://localhost:8081";
    private final RestTemplate restTemplate;
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;
    private Long memberId = 3L;
    private Long productId = 5L;
    private Long baasMemberId = 1L;

    @Override
    @Transactional
    public void issueAccountAndCard(IssueRequest issueRequest) {
        Child child = getChild(memberId);
        Parent parent = getParentOfChild(memberId);
        String accountPassword = parent.getSimplePassword();

        /**
         * BaaS에서 member가 존재하는지 조회한 후 없을 때만 생성해야 함
         * 현재 BaaS에 bankMember 여부를 조회하는 API가 없음 일단 무조건 wekids 자식회원은 은행 회원이 아니라고 가정하고 구현
         * 추후에 BaaS API 추가하고나서 bankMember인지 여부 확인해서 이미 회원이면 해당 정보 이용해서 계좌랑 카드 생성하도록 수정 필요
         */

        MemberBaasRequest memberBaasRequest = MemberBaasRequest.of(child.getName(), child.getBirthday(), issueRequest.getResidentRegistrationNumber(), baasMemberId);
        Long bankMemberId = createBankMember(memberBaasRequest);

        child.saveBankMemberId(bankMemberId);
        child.saveSimplePassword(accountPassword);

        AccountBaasRequest accountBaasRequest = AccountBaasRequest.of(bankMemberId, accountPassword, productId);
        Account account = createAccount(accountBaasRequest, child);

        CardBaasRequest cardBaasRequest = CardBaasRequest.of(account.getAccountNumber(), child.getBankMemberId(), issueRequest.getPassword());
        createCard(cardBaasRequest, account);
    }

    private Long createBankMember(MemberBaasRequest memberBaasRequest) {
        String bankMemberBaasUrl = BAAS_URL + "/api/v1/bank-members";
        ResponseEntity<MemberBaasResponse> memberResponse = restTemplate.postForEntity(bankMemberBaasUrl, memberBaasRequest, MemberBaasResponse.class);

        Long bankMemberId = memberResponse.getBody().getBankMemberId();

        return bankMemberId;
    }

    private Account createAccount(AccountBaasRequest accountRequest, Child child) {
        String accountBaasUrl = BAAS_URL + "/api/v1/accounts";

        ResponseEntity<AccountBaasResponse> accountResponse = restTemplate.postForEntity(accountBaasUrl, accountRequest, AccountBaasResponse.class);

        Account account = Account.of(accountResponse.getBody().getAccountNumber(), child);

        return accountRepository.save(account);
    }

    private Card createCard(CardBaasRequest cardRequest, Account account) {
        String cardBaasUrl = BAAS_URL + "/api/v1/cards";

        ResponseEntity<CardBaasResponse> cardResponse = restTemplate.postForEntity(cardBaasUrl, cardRequest, CardBaasResponse.class);

        String cardName = createCardName(account.getMember().getName());

        Card card = Card.of(cardResponse.getBody(), cardRequest.getPassword(), account, cardName);

        return cardRepository.save(card);
    }

    private String createCardName(String name) {
        return name.substring(name.length() - 2) + "핑";
    }


    private Child getChild(Long memberId) {
        return childRepository.findById(memberId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, String.format("회원 아이디: %s", memberId)));
    }

    private Parent getParentOfChild(Long childId) {
        return parentRepository.findParentByChildId(childId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, String.format("자식 아이디: ", childId)));
    }

}