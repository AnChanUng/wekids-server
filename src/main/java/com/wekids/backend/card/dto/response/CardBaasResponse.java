package com.wekids.backend.card.dto.response;

import com.wekids.backend.card.domain.Card;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CardBaasResponse {
    private String cardNumber;
    private LocalDate validThru;
    private String cvc;
    private String bankMemberName;
    private LocalDateTime newDate;

    public static Card of(String cardNumber, LocalDate validThru, String cvc, String memberName, LocalDateTime newDate) {
        return Card.builder()
                .cardNumber(cardNumber)
                .validThru(validThru)
                .cvc(cvc)
                .memberName(memberName)
                .newDate(newDate)
                .build();
    }
}
