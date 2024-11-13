package com.wekids.backend.account.domain;

import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.common.entity.BaseTime;
import com.wekids.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class Account extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String accountNumber;

    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private AccountState state;

    private LocalDateTime inactiveDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private Member member;
}
