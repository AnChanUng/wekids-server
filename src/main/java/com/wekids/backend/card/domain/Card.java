package com.wekids.backend.card.domain;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.baas.dto.response.CardCreateResponse;
import com.wekids.backend.card.domain.enums.CardState;
import com.wekids.backend.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private LocalDate validThru;

    @Column(nullable = false)
    private String cvc;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String cardName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CardState state = CardState.ACTIVE;

    private LocalDateTime inactiveDate;

    @Column(nullable = false)
    private LocalDateTime newDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public void updateState(CardState cardState){
        this.state = cardState;
    }

    public static Card of(CardCreateResponse cardCreateResponse, String cardPassword, Account account, String cardName) {
        return Card.builder()
                .cardNumber(cardCreateResponse.getCardNumber())
                .validThru(cardCreateResponse.getValidThru())
                .cvc(cardCreateResponse.getCvc())
                .memberName(cardCreateResponse.getBankMemberName())
                .cardName(cardName)
                .newDate(cardCreateResponse.getNewDate())
                .password(cardPassword)
                .account(account)
                .build();
    }
}
