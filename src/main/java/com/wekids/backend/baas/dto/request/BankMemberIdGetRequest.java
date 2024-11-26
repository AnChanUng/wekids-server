package com.wekids.backend.baas.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BankMemberIdGetRequest {
    private String residentRegistrationNumber;
}
