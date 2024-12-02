package com.wekids.backend.baas.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Getter
@Builder
public class AccountTransactionGetRequest {
    private String accountNumber;
    private LocalDateTime start;
    private LocalDateTime end;
    private String type;
    private Integer page;
    private Integer size;

    public static AccountTransactionGetRequest of(String accountNumber, LocalDateTime start, LocalDateTime end, String type, Pageable pageable){
        return AccountTransactionGetRequest.builder()
                .accountNumber(accountNumber)
                .start(start)
                .end(end)
                .type(type)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
    }
}