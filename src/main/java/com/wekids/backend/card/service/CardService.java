package com.wekids.backend.card.service;

import com.wekids.backend.admin.dto.request.CardStateRequest;

public interface CardService {
    public void changeState(Long cardId, CardStateRequest request);
}
