package com.wekids.backend.admin.dto.response;

import com.wekids.backend.card.domain.Card;
import com.wekids.backend.card.domain.enums.CardState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {
    private Long cardId;
    private String cardNumber;
    private CardState state;
    private LocalDateTime inactiveDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CardResponse from(Card card) {
        return CardResponse.builder()
                .cardId(card.getId())
                .cardNumber(card.getCardNumber().substring(0, 10) + "****-****")
                .state(card.getState())
                .inactiveDate(card.getInactiveDate())
                .createdAt(card.getCreatedAt())
                .updatedAt(card.getUpdatedAt())
                .build();
    }
}
