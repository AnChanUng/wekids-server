package com.wekids.backend.baas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
