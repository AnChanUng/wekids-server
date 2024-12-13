package com.wekids.backend.accountTransaction.domain;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import com.wekids.backend.baas.dto.response.AccountTransactionResponse;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String receiver;

    @Builder.Default
    private String memo = "";

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @ToString.Exclude
    private Account account;

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public static AccountTransaction of(Account account, AccountTransactionResponse response){
        return AccountTransaction.builder()
                .account(account)
                .title(response.getTitle())
                .amount(BigDecimal.valueOf(response.getAmount()))
                .type(TransactionType.from(response.getType()))
                .sender(response.getSender())
                .receiver(response.getReceiver())
                .balance(BigDecimal.valueOf(response.getBalance()))
                .createdAt(response.getTransactionDate())
                .build();
    }
}
