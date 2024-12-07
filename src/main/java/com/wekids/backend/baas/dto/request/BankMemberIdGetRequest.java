package com.wekids.backend.baas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankMemberIdGetRequest {
    private String residentRegistrationNumber;
}
