package com.wekids.backend.account.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.dto.response.AccountResponse;
import com.wekids.backend.account.dto.response.BaasAccountResponse;
import com.wekids.backend.account.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock private AccountRepository accountRepository;
    @InjectMocks
    private AccountServiceImpl accountService;


    @Test
    void 계좌를_가져온다() {
        Long baasMemberId = 1L;
        Long bankMemberId = 1L;

        List<BaasAccountResponse> mockResponseList = Arrays.asList(
                new BaasAccountResponse("1111111111111", "우리은행", BigDecimal.valueOf(999999), "ACTIVE", "강현우", "우리 아이 행복 통장", "입출금"),
                new BaasAccountResponse("1111111111111", "우리은행", BigDecimal.valueOf(999999), "ACTIVE", "강현우", "우리 아이 행복 통장", "입출금")
        );

        ResponseEntity<List<BaasAccountResponse>> responseEntity = new ResponseEntity<>(mockResponseList, HttpStatus.OK);

        String url = "http://localhost:9090/api/v1/baas-members/{baasMemberId}/bank-members/{bankMemberId}/accounts";

        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                isNull(),
                eq(new ParameterizedTypeReference<List<BaasAccountResponse>>() {}),
                anyMap()
        )).thenReturn(responseEntity);

        List<AccountResponse> result = accountService.getAccount();

        assertEquals(2, result.size());
        assertEquals("우리은행", result.get(0).getBankName());
        assertEquals(BigDecimal.valueOf(999999), result.get(0).getBalance());

        verify(restTemplate, times(1)).exchange(
                eq(url),
                eq(HttpMethod.GET),
                isNull(),
                eq(new ParameterizedTypeReference<List<BaasAccountResponse>>() {}),
                anyMap()
        );
    }
}
