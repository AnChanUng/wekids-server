package com.wekids.backend.card.domain;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.card.domain.enums.CardState;
import com.wekids.backend.card.dto.request.CardBaasRequest;
import com.wekids.backend.card.dto.response.CardBaasResponse;
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
    @Column(nullable = false, columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private CardState state;

    private LocalDateTime inactiveDate;

    @Column(nullable = false)
    private LocalDateTime newDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public static Card createFromResponse( CardBaasResponse cardResponse, CardBaasRequest cardRequest, Account account, String cardName) {
        return Card.builder()
                .cardNumber(cardResponse.getCardNumber())
                .validThru(cardResponse.getValidThru())
                .cvc(cardResponse.getCvc())
                .memberName(cardResponse.getBankMemberName())
                .cardName(cardName)
                .newDate(cardResponse.getNewDate())
                .password(cardRequest.getPassword())
                .account(account)
                .state(CardState.ACTIVE)
                .build();
    }
}
