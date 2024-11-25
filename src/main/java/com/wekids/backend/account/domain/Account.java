package com.wekids.backend.account.domain;

import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.common.entity.BaseTime;
import com.wekids.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String accountNumber;

    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    @Builder.Default
    private AccountState state = AccountState.ACTIVE;

    private LocalDateTime inactiveDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private Member member;

    public static Account of(String accountNumber, Member member) {
        return Account.builder()
                .accountNumber(accountNumber)
                .balance(BigDecimal.ZERO)
                .member(member)
                .build();
    }
}
