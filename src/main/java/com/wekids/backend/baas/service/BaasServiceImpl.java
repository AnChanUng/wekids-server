package com.wekids.backend.baas.service;

import com.wekids.backend.account.dto.response.BaasAccountResponse;
import com.wekids.backend.baas.aop.BaasLogAndHandleException;
import com.wekids.backend.baas.dto.request.*;
import com.wekids.backend.baas.dto.response.*;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.repository.ParentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
public class BaasServiceImpl implements BaasService {
    private final ParentRepository parentRepository;
    private final RestTemplate restTemplate;
    private final String BAAS_URL;
    private final Long BAAS_MEMBER_ID;
    private final Long PRODUCT_ID;

    public BaasServiceImpl(ParentRepository parentRepository, RestTemplate restTemplate, @Value("${baas.api.baas-url}") String baasUrl, @Value("${baas.id.baas-member-id}") Long baasMemberId, @Value("${baas.id.product-id}") Long productId) {
        this.parentRepository = parentRepository;
        this.restTemplate = restTemplate;
        BAAS_URL = baasUrl;
        BAAS_MEMBER_ID = baasMemberId;
        PRODUCT_ID = productId;
    }

    @Override
    @BaasLogAndHandleException
    public List<AllAccountResponse> showAccounts(Long memberId) {
        Parent parent = findParentByMemberId(memberId);

        String url = BAAS_URL + "/api/v1/baas-members/{baasMemberId}/bank-members/{bankMemberId}/accounts";
        ResponseEntity<List<BaasAccountResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BaasAccountResponse>>() {
                },
                BAAS_MEMBER_ID,
                parent.getBankMemberId()
        );

        return response.getBody().stream()
                .map(AllAccountResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @BaasLogAndHandleException
    public Long createBankMember(BankMemberCreateRequest bankMemberCreateRequest) {
        String url = BAAS_URL + "/api/v1/bank-members";

        bankMemberCreateRequest.setBaasMemberId(BAAS_MEMBER_ID);

        ResponseEntity<BankMemberCreateResponse> response = restTemplate.postForEntity(url, bankMemberCreateRequest, BankMemberCreateResponse.class);

        return response.getBody().getBankMemberId();
    }

    @Override
    @BaasLogAndHandleException
    public AccountCreateResponse createAccount(AccountCreateRequest accountCreateRequest) {
        String url = BAAS_URL + "/api/v1/accounts";

        accountCreateRequest.setProductId(PRODUCT_ID);

        ResponseEntity<AccountCreateResponse> response = restTemplate.postForEntity(url, accountCreateRequest, AccountCreateResponse.class);

        return response.getBody();
    }

    @Override
    @BaasLogAndHandleException
    public CardCreateResponse createCard(CardCreateRequest cardCreateRequest) {
        String url = BAAS_URL + "/api/v1/cards";

        ResponseEntity<CardCreateResponse> response = restTemplate.postForEntity(url, cardCreateRequest, CardCreateResponse.class);

        return response.getBody();
    }

    @Override
    @BaasLogAndHandleException
    public TransferResponse transfer(TransferRequest transferRequest) {
        String url = BAAS_URL + "/api/v1/transactions";

        ResponseEntity<TransferResponse> response = restTemplate.postForEntity(url, transferRequest, TransferResponse.class);

        return response.getBody();
    }

    @Override
    @BaasLogAndHandleException
    public List<AccountTransactionResponse> getAccountTransactionList(AccountTransactionGetRequest accountTransactionGetRequest) {
        String url = BAAS_URL + "/api/v1/getTransactions";

        ResponseEntity<AccountTransactionResponse[]> response = restTemplate.postForEntity(url, accountTransactionGetRequest, AccountTransactionResponse[].class);
        AccountTransactionResponse[] body = response.getBody();

        return Arrays.stream(body).toList();
    }

    private Parent findParentByMemberId(Long memberId) {
        return parentRepository.findById(memberId).orElseThrow(
                () -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, memberId + "가 없습니다."));
    }
}