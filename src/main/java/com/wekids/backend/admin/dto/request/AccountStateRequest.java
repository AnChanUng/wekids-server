package com.wekids.backend.admin.dto.request;

import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.card.domain.enums.CardState;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AccountStateRequest {
    @NotNull(message = "Account state is required.")
    private AccountState state;
}
