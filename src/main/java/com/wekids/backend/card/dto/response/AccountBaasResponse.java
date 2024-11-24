package com.wekids.backend.card.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountBaasResponse {
    private String accountNumber; // 계좌번호
    private String type;          // 계좌 종류
    private LocalDateTime expireDate; // 만료일시
}
