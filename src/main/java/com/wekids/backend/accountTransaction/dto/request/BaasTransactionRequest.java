package com.wekids.backend.accountTransaction.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Getter
@Builder
public class BaasTransactionRequest {
    private String accountNumber;
    private LocalDate start;
    private LocalDate end;
    private String type;
    private Integer page;
    private Integer size;

    public static BaasTransactionRequest of(String accountNumber, LocalDate start, LocalDate end, String type, Pageable pageable){
        return BaasTransactionRequest.builder()
                .accountNumber(accountNumber)
                .start(start)
                .end(end)
                .type(type)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
    }
}