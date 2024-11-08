package com.wekids.backend.account.domain;

import com.wekids.backend.account.domain.enums.AccountDesignType;
import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.common.entity.BaseTime;
import com.wekids.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class Account extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, length = 50)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVE'")
    private AccountState state;

    @Enumerated(EnumType.STRING)
    @Column(name = "design_type", nullable = false)
    private AccountDesignType designType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member;
}
