package com.wekids.backend.baas.service;

import com.wekids.backend.baas.dto.request.*;
import com.wekids.backend.baas.dto.response.*;

import java.util.List;

public interface BaasService {
    List<AccountGetResponse> showAccounts(Long bankMemberId);
    Long createBankMember(BankMemberCreateRequest bankMemberCreateRequest);
    AccountCreateResponse createAccount(AccountCreateRequest accountCreateRequest);
    CardCreateResponse createCard(CardCreateRequest cardCreateRequest);
    TransferResponse transfer(TransferRequest transferRequest);
    List<AccountTransactionResponse> getAccountTransactionList(AccountTransactionGetRequest accountTransactionGetRequest);
    AccountGetResponse getAccount(AccountGetRequest accountGetRequest);
    BankMemberIdResponse getBankMemberId(BankMemberIdGetRequest bankMemberIdGetRequest); // TODO: 2024-12-03 삭제 필요
    BankMemberIdResponse registerWekids(WekidsRegistrationRequest wekidsRegistrationRequest);
}
