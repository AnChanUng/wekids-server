package com.wekids.backend.baas.dto.request;

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
            Long baasMemberId,
            String cardNumber,
            String cvc,
            String password,
            CardState state
    ){
        return CardStateChangeRequest.builder()
                .bankMemberId(bankMemberId)
                .baasMemberId(baasMemberId)
                .cardNumber(cardNumber)
                .cvc(cvc)
                .password(password)
                .state(state)
                .build();
    }
}
