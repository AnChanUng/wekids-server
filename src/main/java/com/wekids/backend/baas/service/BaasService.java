package com.wekids.backend.baas.service;

import com.wekids.backend.baas.dto.request.*;
import com.wekids.backend.baas.dto.response.AccountCreateResponse;
import com.wekids.backend.baas.dto.response.AccountTransactionGetResponse;
import com.wekids.backend.baas.dto.response.AllAccountResponse;
import com.wekids.backend.baas.dto.response.CardCreateResponse;

import java.util.List;

public interface BaasService {

    List<AllAccountResponse> showAccounts(Long memberId);
    Long createBankMember(BankMemberCreateRequest bankMemberCreateRequest);
    AccountCreateResponse createAccount(AccountCreateRequest accountCreateRequest);
    CardCreateResponse createCard(CardCreateRequest cardCreateRequest);
    void transfer(TransferRequest transferRequest);
    List<AccountTransactionGetResponse> getAccountTransactionList(AccountTransactionGetRequest accountTransactionGetRequest);
}
