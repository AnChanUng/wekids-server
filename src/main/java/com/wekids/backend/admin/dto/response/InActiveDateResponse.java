package com.wekids.backend.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InActiveDateResponse {
    LocalDateTime inactiveDate;

    public static InActiveDateResponse from(LocalDateTime localDateTime){
        return new InActiveDateResponse(localDateTime);
    }
}
