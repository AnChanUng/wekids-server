package com.wekids.backend.accountTransaction.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @NotBlank(message = "보내는 분 계좌번호는 빈값일 수 없습니다")
    @Pattern(regexp = "^\\d{4}-\\d{3}-\\d{6}")
    private String parentAccountNumber;
    @NotBlank(message = "받는 분 계좌번호는 빈값일 수 없습니다")
    @Pattern(regexp = "^\\d{4}-\\d{3}-\\d{6}")
    private String childAccountNumber;
    @NotNull
    @Positive(message = "이체 금액은 0보다 커야 합니다")
    private BigDecimal amount;    //이체 금액
    @NotBlank(message = "보내는 분은 빈값일 수 없습니다")
    private String sender;  //부모이름
    @NotBlank(message = "받는 분은 빈값일 수 없습니다")
    private String receiver;    //자식이름

}
