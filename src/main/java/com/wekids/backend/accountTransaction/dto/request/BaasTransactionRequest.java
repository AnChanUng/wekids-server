package com.wekids.backend.accountTransaction.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class BaasTransactionRequest {
    private String accountNumber;
    private LocalDateTime start;
    private LocalDateTime end;
    private String type;
    private Integer page;
    private Integer size;

    public static BaasTransactionRequest of(String accountNumber, LocalDateTime start, LocalDateTime end, String type, Pageable pageable){
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