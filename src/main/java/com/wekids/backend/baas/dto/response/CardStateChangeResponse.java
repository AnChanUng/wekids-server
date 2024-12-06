package com.wekids.backend.baas.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardStateChangeResponse {
    private LocalDateTime inactiveDate;
}
