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
public class ParentAccountRegistrationRequest {
    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{3}-\\d{6}", message = "게좌번호 형식이 맞지않습니다.")
    private String accountNumber;
}
