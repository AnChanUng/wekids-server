package com.wekids.backend.accountTransaction.domain;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import com.wekids.backend.accountTransaction.dto.response.BaasTransactionResponse;
import com.wekids.backend.accountTransaction.dto.request.TransactionRequest;
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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @ToString.Exclude
    private Account account;


    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public static AccountTransaction createTransaction(TransactionRequest request, TransactionType type, BigDecimal
            balance, Account account) {
        return AccountTransaction.builder()
                .title(request.getSender())
                .type(type)
                .amount(request.getAmount())
                .balance(balance)
                .sender(request.getSender())
                .receiver(request.getReceiver())
                .memo("")
                .createdAt(LocalDateTime.now())
                .account(account)
                .build();
    }

    public static AccountTransaction of(Account account, BaasTransactionResponse response){
        return AccountTransaction.builder()
                .account(account)
                .title(response.getTitle())
                .amount(new BigDecimal(response.getAmount()))
                .type(TransactionType.from(response.getType()))
                .sender(response.getSender())
                .receiver(response.getReceiver())
                .balance(new BigDecimal(response.getBalance()))
                .createdAt(response.getTransactionDate())
                .build();
    }
}
