package com.wekids.backend.baas.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@Getter
@Builder
public class BankMemberCreateRequest {
    private String name;
    private LocalDate birthday;
    private String residentRegistrationNumber;
    private Long baasMemberId;

    public static BankMemberCreateRequest of(String name, LocalDate birthday, String residentRegistrationNumber) {
        return BankMemberCreateRequest.builder()
                .name(name)
                .birthday(birthday)
                .residentRegistrationNumber(residentRegistrationNumber)
                .build();
    }

    public void setBaasMemberId(Long baasMemberId) {
        this.baasMemberId = baasMemberId;
    }
}
