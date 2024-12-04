package com.wekids.backend.card.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequest {
    @NotNull
    @Positive
    private Long childId;

    @NotBlank
    @Pattern(regexp = "^\\d{6}-\\d{7}$")
    private String residentRegistrationNumber;

    @NotBlank
    @Pattern(regexp = "^\\d{4}")
    private String accountPassword;

    @NotBlank
    @Pattern(regexp = "^\\d{4}")
    private String cardPassword;
}
