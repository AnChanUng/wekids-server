package com.wekids.backend.baas.service;

import com.wekids.backend.baas.dto.response.AllAccountResponse;

import java.util.List;

public interface BaasService {

    List<AllAccountResponse> showAccounts(Long memberId);
}
