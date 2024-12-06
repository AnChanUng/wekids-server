package com.wekids.backend.card.service;

import com.wekids.backend.admin.dto.request.CardStateRequest;
import com.wekids.backend.admin.dto.response.InActiveDateResponse;

public interface CardService {
    public InActiveDateResponse changeState(Long cardId, CardStateRequest request);
}
