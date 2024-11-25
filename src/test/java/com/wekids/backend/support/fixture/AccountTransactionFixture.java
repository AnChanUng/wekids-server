package com.wekids.backend.support.fixture;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class AccountTransactionFixture {
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String title = "카카오페이"; // 더미 데이터 제목
    @Builder.Default
    private TransactionType type = TransactionType.DEPOSIT; // 기본 거래 유형
    @Builder.Default
    private BigDecimal amount = BigDecimal.valueOf(100.00); // 기본 거래 금액
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(1000.00); // 기본 잔액
    @Builder.Default
    private String sender = "Sender Name"; // 더미 송신자
    @Builder.Default
    private String receiver = "Receiver Name"; // 더미 수신자
    @Builder.Default
    private String memo; // 메모
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 시간
    @Builder.Default
    private Account account = AccountFixture.builder().build().account();

    public AccountTransaction accountTransaction() {
        return AccountTransaction.builder()
                .id(id)
                .title(title)
                .type(type)
                .amount(amount)
                .balance(balance)
                .sender(sender)
                .receiver(receiver)
                .memo(memo)
                .createdAt(createdAt)
                .account(account)
                .build();
    }
}