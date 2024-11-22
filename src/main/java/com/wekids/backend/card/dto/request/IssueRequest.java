package com.wekids.backend.card.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IssueRequest {
    @NotBlank
    @Pattern(regexp = "^\\d{6}-\\d{7}$")
    private String residentRegistrationNumber;
    @NotBlank
    private String password;
}
