package com.wekids.backend.card.service;

import com.wekids.backend.admin.dto.request.CardStateRequest;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.card.repository.CardRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    @Override
    @Transactional
    public void updateCardState(Long cardId, CardStateRequest request) {
        Card card = findCardByCardId(cardId);
        card.updateState(request.getState());
    }


    private Card findCardByCardId(Long cardId){
        return cardRepository.findById(cardId).orElseThrow(()->
                new WekidsException(ErrorCode.CARD_NOT_FOUND, cardId + "의 값을 가진 카드 인스턴스는 존재하지 않습니다"));
    }
}
