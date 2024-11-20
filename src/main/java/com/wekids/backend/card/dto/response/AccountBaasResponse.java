package com.wekids.backend.card.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AccountBaasResponse {
    private String accountNumber; // 계좌번호
    private String type;          // 계좌 종류
    private LocalDateTime expireDate; // 만료일시
}
