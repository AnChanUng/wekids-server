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

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "" + memberId));

        AccountBaasRequest accountBaasRequest = AccountBaasRequest.builder()
                .bankMemberId(member.getBankMemberId())
                .productId(productId)
                .password(parent.getSimplePassword())
                .build();

        Account account = createAccount(accountBaasRequest, member);

    }

    private Account createAccount(AccountBaasRequest accountRequest, Member member) {
        String accountBaasUrl = "http://localhost:8081/api/v1/accounts";

        AccountBaasResponse accountResponse = restTemplate.postForObject(accountBaasUrl, accountRequest, AccountBaasResponse.class);

        Account account = Account.builder()
                .accountNumber(accountResponse.getAccountNumber())
                .balance(BigDecimal.ZERO)
                .state(AccountState.ACTIVE)
                .member(member)
                .build();

        Account savedAccount = accountRepository.save(account);
        log.info("Saved account {}", savedAccount);

        return savedAccount;
    }



}