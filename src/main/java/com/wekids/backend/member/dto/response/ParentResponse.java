package com.wekids.backend.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wekids.backend.account.domain.Account;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.design.domain.enums.ColorType;
import com.wekids.backend.member.domain.Parent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParentResponse {
    String name;
    String accountNumber;
    String profile;
    BigDecimal balance;
    ColorType color;
    CharacterType character;
    Long accountId;

    public static ParentResponse of(Parent parent, Account account, Design design) {
        ParentResponse.ParentResponseBuilder builder = ParentResponse.builder()
                .name(parent.getName())
                .profile(parent.getProfile());

        if (account != null) {
            builder.accountId(account.getId())
                    .accountNumber(account.getAccountNumber())
                    .balance(account.getBalance());
        }

        if (design != null) {
            builder.color(design.getColor());
            builder.character(design.getCharacter());
        }

        return builder.build();
    }

}
