package com.wekids.backend.baas.service;

import com.wekids.backend.baas.dto.request.AccountCreateRequest;
import com.wekids.backend.baas.dto.request.BankMemberCreateRequest;
import com.wekids.backend.baas.dto.request.CardCreateRequest;
import com.wekids.backend.baas.dto.response.AccountCreateResponse;
import com.wekids.backend.baas.dto.response.AllAccountResponse;
import com.wekids.backend.baas.dto.response.CardCreateResponse;

import java.util.List;

public interface BaasService {

    List<AllAccountResponse> showAccounts(Long memberId);
    Long createBankMember(BankMemberCreateRequest bankMemberCreateRequest);
    AccountCreateResponse createAccount(AccountCreateRequest accountCreateRequest);
    CardCreateResponse createCard(CardCreateRequest cardCreateRequest);
}
