package com.wekids.backend.card.domain;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.card.domain.enums.CardState;
import com.wekids.backend.common.entity.BaseTime;
import com.wekids.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
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
}
