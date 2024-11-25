package com.wekids.backend.baas.service;

import com.wekids.backend.account.dto.response.BaasAccountResponse;
import com.wekids.backend.baas.aop.BaasLogAndHandleException;
import com.wekids.backend.baas.dto.request.AccountCreateRequest;
import com.wekids.backend.baas.dto.request.BankMemberCreateRequest;
import com.wekids.backend.baas.dto.request.CardCreateRequest;
import com.wekids.backend.baas.dto.response.AccountCreateResponse;
import com.wekids.backend.baas.dto.response.AllAccountResponse;
import com.wekids.backend.baas.dto.response.BankMemberCreateResponse;
import com.wekids.backend.baas.dto.response.CardCreateResponse;
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
    public Long createBankMember(BankMemberCreateRequest bankMemberCreateRequest) {
        String url = BAAS_URL + "/api/v1/bank-members";

        bankMemberCreateRequest.setBaasMemberId(BAAS_MEMBER_ID);

        ResponseEntity<BankMemberCreateResponse> response = restTemplate.postForEntity(url, bankMemberCreateRequest, BankMemberCreateResponse.class);

        return response.getBody().getBankMemberId();
    }

    @Override
    public AccountCreateResponse createAccount(AccountCreateRequest accountCreateRequest) {
        String url = BAAS_URL + "/api/v1/accounts";

        accountCreateRequest.setProductId(PRODUCT_ID);

        ResponseEntity<AccountCreateResponse> response = restTemplate.postForEntity(url, accountCreateRequest, AccountCreateResponse.class);

        return response.getBody();
    }

    @Override
    public CardCreateResponse createCard(CardCreateRequest cardCreateRequest) {
        String url = BAAS_URL + "/api/v1/cards";

        ResponseEntity<CardCreateResponse> response = restTemplate.postForEntity(url, cardCreateRequest, CardCreateResponse.class);

        return response.getBody();
    }

    private Parent findParentByMemberId(Long memberId) {
        return parentRepository.findById(memberId).orElseThrow(
                () -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, memberId + "가 없습니다."));
    }
}