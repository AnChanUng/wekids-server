package com.wekids.backend.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInquiryAgreeRequest {
    @NotBlank
    @Pattern(regexp = "^\\d{6}-\\d{7}$")
    private String residentRegistrationNumber;
}
