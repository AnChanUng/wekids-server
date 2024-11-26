package com.wekids.backend.baas.service;

import com.wekids.backend.baas.aop.BaasLogAndHandleException;
import com.wekids.backend.baas.dto.request.*;
import com.wekids.backend.baas.dto.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final RestTemplate restTemplate;
    private final String BAAS_URL;
    private final Long BAAS_MEMBER_ID;
    private final Long PRODUCT_ID;

    public BaasServiceImpl(RestTemplate restTemplate, @Value("${baas.api.baas-url}") String baasUrl, @Value("${baas.id.baas-member-id}") Long baasMemberId, @Value("${baas.id.product-id}") Long productId) {
        this.restTemplate = restTemplate;
        BAAS_URL = baasUrl;
        BAAS_MEMBER_ID = baasMemberId;
        PRODUCT_ID = productId;
    }

    @Override
    @BaasLogAndHandleException
    public List<AccountGetResponse> showAccounts(Long bankMemberId) {
        String url = BAAS_URL + "/api/v1/baas-members/{baasMemberId}/bank-members/{bankMemberId}/accounts";

        ResponseEntity<AccountGetResponse[]> response = restTemplate.getForEntity(url, AccountGetResponse[].class, BAAS_MEMBER_ID, bankMemberId);

        return Arrays.stream(response.getBody()).collect(Collectors.toList());
    }

    @Override
    @BaasLogAndHandleException
    public Long createBankMember(BankMemberCreateRequest bankMemberCreateRequest) {
        String url = BAAS_URL + "/api/v1/bank-members";

        bankMemberCreateRequest.setBaasMemberId(BAAS_MEMBER_ID);

        ResponseEntity<BankMemberIdResponse> response = restTemplate.postForEntity(url, bankMemberCreateRequest, BankMemberIdResponse.class);

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

    @Override
    @BaasLogAndHandleException
    public AccountGetResponse getAccount(AccountGetRequest accountGetRequest) {
        String url = BAAS_URL + "/api/v1/getAccounts";

        ResponseEntity<AccountGetResponse> response = restTemplate.postForEntity(url, accountGetRequest, AccountGetResponse.class);

        return response.getBody();
    }

    @Override
    public BankMemberIdResponse getBankMemberId(BankMemberIdGetRequest bankMemberIdGetRequest) {
        String url = BAAS_URL + "/api/v1/bank-members/getId";

        ResponseEntity<BankMemberIdResponse> response = restTemplate.postForEntity(url, bankMemberIdGetRequest, BankMemberIdResponse.class);

        return response.getBody();
    }
}