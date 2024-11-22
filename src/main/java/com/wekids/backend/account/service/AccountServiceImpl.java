package com.wekids.backend.account.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.dto.response.AccountResponse;
import com.wekids.backend.account.dto.response.BaasAccountResponse;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    private final RestTemplate restTemplate;

    public AccountServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<AccountResponse> getAccount() {
        Long baasMemberId = 1L;
        Long bankMemberId = 1L;

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("baasMemberId", String.valueOf(baasMemberId));
        uriVariables.put("bankMemberId", String.valueOf(bankMemberId));

        String url = "http://localhost:9090/api/v1/baas-members/{baasMemberId}/bank-members/{bankMemberId}/accounts";


        ResponseEntity<List<BaasAccountResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BaasAccountResponse>>() {}, // List
                uriVariables
        );

        if (response == null || response.getBody() == null) {
            throw new WekidsException(ErrorCode.INVALID_INPUT, "응답이 없습니다.");
        }


        return response.getBody().stream()
                .map(AccountResponse::new)
                .collect(Collectors.toList());
    }
}


