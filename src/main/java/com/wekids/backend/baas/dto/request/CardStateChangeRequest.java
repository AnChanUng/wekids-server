package com.wekids.backend.baas.dto.request;

import com.wekids.backend.card.domain.Card;
import com.wekids.backend.card.domain.enums.CardState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardStateChangeRequest {
    @NotNull
    private Long bankMemberId;

    @NotNull
    private Long baasMemberId;

    @NotBlank
    private String cardNumber;

    @NotBlank
    @Size(min = 3, max = 3)
    private String cvc;

    @NotBlank
    @Size(min = 4, max = 4)
    private String password;

    @NotNull
    private CardState state;

    public static CardStateChangeRequest of(
            Long bankMemberId,
            Card card,
            CardState state
    ){
        return CardStateChangeRequest.builder()
                .bankMemberId(bankMemberId)
                .cardNumber(card.getCardNumber())
                .cvc(card.getCvc())
                .password(card.getPassword())
                .state(state)
                .build();
    }

    public void setBaasMemberId(Long baasMemberId) {
        this.baasMemberId = baasMemberId;
    }
}
