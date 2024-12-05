package com.wekids.backend.baas.dto.request;

import com.wekids.backend.account.domain.enums.AccountState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStateChangeRequest {
    @NotNull
    @Positive
    private Long bankMemberId;

    @NotNull
    @Positive
    private Long baasMemberId;

    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{3}-\\d{6}$")
    private String accountNumber;

    @NotBlank
    private String password;

    @NotNull
    private AccountState state;

    public static AccountStateChangeRequest of(
            Long bankMemberId,
            Long baasMemberId,
            String accountNumber,
            String password,
            AccountState state
    ) {
        return AccountStateChangeRequest.builder()
                .bankMemberId(bankMemberId)
                .baasMemberId(baasMemberId)
                .accountNumber(accountNumber)
                .password(password)
                .state(state)
                .build();
    }
}
