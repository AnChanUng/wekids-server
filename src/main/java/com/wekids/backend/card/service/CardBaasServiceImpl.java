package com.wekids.backend.card.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.card.dto.request.AccountBaasRequest;
import com.wekids.backend.card.dto.request.CardBaasRequest;
import com.wekids.backend.card.dto.request.PasswordRequest;
import com.wekids.backend.card.dto.response.AccountBaasResponse;
import com.wekids.backend.card.dto.response.CardBaasResponse;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.card.domain.enums.CardState;
import com.wekids.backend.card.repository.CardRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CardBaasServiceImpl implements CardBaasService {

    private final RestTemplate restTemplate;
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    private Long memberId = 2L;
    private Long productId = 5L;

    @Override
    @Transactional
    public void issueAccountAndCard(PasswordRequest passwordRequest) {
        Member member = findByBankMemberId(memberId);
        Parent parent = findParentByChildId(memberId);

        AccountBaasRequest accountBaasRequest = AccountBaasRequest.createFromMemberAndParent(member, parent, productId);
        Account account = createAccount(accountBaasRequest, member);

        CardBaasRequest cardBaasRequest = CardBaasRequest.createFromAccountAndMember(account, member, passwordRequest);
        createCard(cardBaasRequest, account);
    }

    private Account createAccount(AccountBaasRequest accountRequest, Member member) {
        String accountBaasUrl = "http://localhost:8081/api/v1/accounts";

        AccountBaasResponse accountResponse = restTemplate.postForObject(accountBaasUrl, accountRequest, AccountBaasResponse.class);

        Account account = Account.createFromResponse(accountResponse, member);

        Account savedAccount = accountRepository.save(account);
        log.info("Saved account {}", savedAccount);

        return savedAccount;
    }

    private Card createCard(CardBaasRequest cardRequest, Account account) {
        String cardBaasUrl = "http://localhost:8081/api/v1/cards";

        CardBaasResponse cardResponse = restTemplate.postForObject(cardBaasUrl, cardRequest, CardBaasResponse.class);

        log.info("cardResponse {}", cardResponse);

        String cardName = getLastTwoCharacters(account.getMember().getName()) + "핑";
        Card card = Card.createFromResponse(cardResponse, cardRequest, account, cardName);

        return cardRepository.save(card);
    }

    private String getLastTwoCharacters(String name) {
        return name.substring(name.length() - 2);
    }

    private Member findByBankMemberId(Long memberId) {
        return memberRepository.findByBankMemberId(memberId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, String.format("자식 계정 아이디: %d를 찾을 수 없습니다.", memberId)));
    }

    private Parent findParentByChildId(Long memberId) {
        return memberRepository.findParentByChildId(memberId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, String.format("부모 계정 아이디: %d를 찾을 수 없습니다.", memberId)));
    }

}