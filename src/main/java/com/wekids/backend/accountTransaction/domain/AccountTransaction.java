package com.wekids.backend.accountTransaction.domain;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'ACTIVE'")
    private TransactionType type;

    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String receiver;

    private String memo;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @ToString.Exclude
    private Account account;
}
