package com.wekids.backend.card.service;

import com.wekids.backend.admin.dto.request.CardStateRequest;
import com.wekids.backend.baas.dto.request.CardStateChangeRequest;
import com.wekids.backend.baas.dto.response.CardStateChangeResponse;
import com.wekids.backend.baas.service.BaasService;
import com.wekids.backend.card.domain.Card;
import com.wekids.backend.card.repository.CardRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.repository.MemberRepository;
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
    private final BaasService baasService;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void changeState(Long cardId, CardStateRequest request) {
        Card card = findCardByCardId(cardId);

        Long bankMemberId = findBankMemberIdByCardId(cardId);

        CardStateChangeRequest cardStateChangeRequest = CardStateChangeRequest.of(bankMemberId, card, request.getState());

        CardStateChangeResponse cardStateChangeResponse = baasService.changeCardState(cardStateChangeRequest);

        card.updateState(request.getState(), cardStateChangeResponse.getInactiveDate());
    }

    private Card findCardByCardId(Long cardId){
        return cardRepository.findById(cardId).orElseThrow(()->
                new WekidsException(ErrorCode.CARD_NOT_FOUND, cardId + "의 값을 가진 카드 인스턴스는 존재하지 않습니다"));
    }

    private Long findBankMemberIdByCardId(Long cardId){
        return memberRepository.findBankMemberIdByCardId(cardId).orElseThrow(()->
                new WekidsException(ErrorCode.INVALID_INPUT, cardId+"의 고객은 bankMemberId가 존재 하지 않습니다"));
    }
}
