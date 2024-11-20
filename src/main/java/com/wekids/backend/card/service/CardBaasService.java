package com.wekids.backend.card.service;

import com.wekids.backend.card.dto.request.PasswordRequest;

public interface CardBaasService {
    void issueAccountAndCard(PasswordRequest passwordRequest);

}
