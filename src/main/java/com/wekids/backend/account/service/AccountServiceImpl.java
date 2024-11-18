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
        //String baasMemberId;
        //String bankMemberId;
        //Map<String, String> uriVariables = new HashMap<>();
        //uriVariables.put("baasMemberId", baasMemberId);
        //uriVariables.put("bankMemberId", bankMemberId);
        //String url = "http://localhost:8080/api/v1/bass-members/{baasMemberId}/bank-members/{bankMemberId}/accounts";
        //ResponseEntity<BaasAccountResponse[]> response = restTemplate.getForEntity(url, BaasAccountResponse[].class, uriVariables);
        //BaasAccountResponse[] responseArray = response.getBody();
        //List<BaasAccountResponse> responseList = Arrays.asList(responseArray);
        List<BaasAccountResponse> responseList = createDummyData(); // 임시
        return responseList.stream().map(
                AccountResponse::new
        ).collect(Collectors.toList());
    }

    public List<Account> getAllAccounts(long id) {
        return Optional.of(accountRepository.findAll())
                .filter(accounts -> !accounts.isEmpty())
                .orElseThrow(() -> new WekidsException(ErrorCode.ACCOUNT_NOT_FOUND, "계좌가 한개도 존재하지 않습니다."));
    }

    public List<BaasAccountResponse> createDummyData(){
        List<BaasAccountResponse> accounts = new ArrayList<>();

        accounts.add(new BaasAccountResponse(
                "111-111-1111",
                "우리은행",
                BigDecimal.valueOf(999999),
                "",
                "고객명",
                "상품명",
                "입출금통장"
        ));
        accounts.add(new BaasAccountResponse(
                "222-222-2222",
                "우리은행",
                BigDecimal.valueOf(888888),
                "",
                "고객명",
                "상품명",
                "입출금통장"
        ));
        return accounts;

    }
}


