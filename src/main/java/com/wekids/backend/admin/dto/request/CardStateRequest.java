package com.wekids.backend.admin.dto.request;

import com.wekids.backend.card.domain.enums.CardState;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CardStateRequest {
    @NotNull(message = "Card state is required.")
    private CardState state;
}
