package com.wekids.backend.card.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRequest {
    @NotBlank
    private String password;
}
