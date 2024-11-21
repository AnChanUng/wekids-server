package com.wekids.backend.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.domain.enums.CharacterType;
import com.wekids.backend.design.domain.enums.ColorType;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.enums.CardState;
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
public class ChildResponse {
    Long childId;
    String name;
    String accountNumber;
    String profile;
    BigDecimal balance;
    Long accountId;
    CardState cardState;
    ColorType color;
    CharacterType character;

    public static ChildResponse of(Child child, Account account, Design design) {
        ChildResponse.ChildResponseBuilder builder = ChildResponse.builder()
                .childId(child.getId())
                .name(child.getName())
                .profile(child.getProfile())
                .cardState(child.getCardState());

        if(account != null){
            builder.accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .accountId(account.getId());
        }

        if (design != null) {
            builder.color(design.getColor())
                    .character(design.getCharacter());
        }

        return builder.build();
    }
}
