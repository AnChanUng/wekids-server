package com.wekids.backend.card.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.baas.dto.request.AccountCreateRequest;
import com.wekids.backend.baas.dto.request.BankMemberCreateRequest;
import com.wekids.backend.baas.dto.request.CardCreateRequest;
import com.wekids.backend.baas.dto.response.AccountCreateResponse;
import com.wekids.backend.baas.dto.response.BankMemberIdResponse;
import com.wekids.backend.baas.dto.response.CardCreateResponse;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.card.dto.request.IssueRequest;
import com.wekids.backend.card.repository.CardRepository;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.repository.ChildRepository;
import com.wekids.backend.member.repository.ParentRepository;
import com.wekids.backend.support.fixture.AccountFixture;
import com.wekids.backend.support.fixture.CardFixture;
import com.wekids.backend.support.fixture.ChildFixture;
import com.wekids.backend.support.fixture.ParentFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardIssueServiceTest {
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ChildRepository childRepository;
    @Mock
    private ParentRepository parentRepository;
    @InjectMocks
    private CardIssueServiceImpl cardIssueService;

    Long memberId = 3L;
    Long productId = 5L;
    Long baasMemberId = 1L;
    String BAAS_URL = "http://localhost:8081";

    String residentRegistrationNumber = "141201-3234567";
    String password = "1234";
    IssueRequest issueRequest = IssueRequest.builder()
            .residentRegistrationNumber(residentRegistrationNumber)
            .password(password)
            .build();

    @Test
    void 성공() {
        Child child = ChildFixture.builder().build().child();
        Parent parent = ParentFixture.builder().build().parent();

        when(childRepository.findById(memberId)).thenReturn(Optional.of(child));
        when(parentRepository.findParentByChildId(memberId)).thenReturn(Optional.of(parent));

        String bankMemberBaasUrl = BAAS_URL + "/api/v1/bank-members";

        Long bankMemberId = 1L;
        String accountPassword = parent.getSimplePassword();

        BankMemberIdResponse memberBaasResponse = BankMemberIdResponse.builder().bankMemberId(bankMemberId).build();
        when(restTemplate.postForEntity(eq(bankMemberBaasUrl), any(BankMemberCreateRequest.class), eq(BankMemberIdResponse.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(memberBaasResponse));

        String accountBaasUrl = BAAS_URL + "/api/v1/accounts";
        Account account = AccountFixture.builder().build().account();

        AccountCreateResponse accountBaasResponse = AccountCreateResponse.builder().accountNumber(account.getAccountNumber()).type("CHECKING").expireDate(LocalDateTime.MAX).build();
        when(restTemplate.postForEntity(eq(accountBaasUrl), any(AccountCreateRequest.class), eq(AccountCreateResponse.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(accountBaasResponse));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        String cardBaasUrl = BAAS_URL + "/api/v1/cards";
        Card card = CardFixture.builder().build().card();

        CardCreateResponse cardBaasResponse = CardCreateResponse.builder()
                .cardNumber(card.getCardNumber())
                .validThru(card.getValidThru())
                .cvc(card.getCvc())
                .newDate(card.getNewDate())
                .bankMemberName(child.getName())
                .build();
        when(restTemplate.postForEntity(eq(cardBaasUrl), any(CardCreateRequest.class), eq(CardCreateResponse.class))).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(cardBaasResponse));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        cardIssueService.issueAccountAndCard(issueRequest);

        assertEquals(bankMemberId, child.getBankMemberId());
        assertEquals(accountPassword, child.getSimplePassword());

        assertEquals(memberBaasResponse.getBankMemberId(), bankMemberId);

        assertEquals(accountBaasResponse.getAccountNumber(), account.getAccountNumber());

        assertEquals(cardBaasResponse.getCardNumber(), card.getCardNumber());

        verify(childRepository, times(1)).findById(memberId);
        verify(parentRepository, times(1)).findParentByChildId(memberId);
        verify(restTemplate, times(1)).postForEntity(eq(bankMemberBaasUrl), any(BankMemberCreateRequest.class), eq(BankMemberIdResponse.class));
        verify(restTemplate, times(1)).postForEntity(eq(accountBaasUrl), any(AccountCreateRequest.class), eq(AccountCreateResponse.class));
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(restTemplate, times(1)).postForEntity(eq(cardBaasUrl), any(CardCreateRequest.class), eq(CardCreateResponse.class));
        verify(cardRepository, times(1)).save(any(Card.class));
    }
}