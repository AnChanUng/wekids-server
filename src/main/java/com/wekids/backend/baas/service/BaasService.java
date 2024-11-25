package com.wekids.backend.baas.service;

import com.wekids.backend.baas.dto.request.*;
import com.wekids.backend.baas.dto.response.*;

import java.util.List;

public interface BaasService {

    List<AllAccountResponse> showAccounts(Long memberId);
    Long createBankMember(BankMemberCreateRequest bankMemberCreateRequest);
    AccountCreateResponse createAccount(AccountCreateRequest accountCreateRequest);
    CardCreateResponse createCard(CardCreateRequest cardCreateRequest);
    TransferResponse transfer(TransferRequest transferRequest);
    List<AccountTransactionResponse> getAccountTransactionList(AccountTransactionGetRequest accountTransactionGetRequest);
}
