package com.wekids.backend.card.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MemberBaasRequest {
    private String name;
    private LocalDate birthday;
    private String residentRegistrationNumber;
    private Long baasMemberId;

    public static MemberBaasRequest of(String name, LocalDate birthday, String residentRegistrationNumber, Long baasMemberId) {
        return MemberBaasRequest.builder()
                .name(name)
                .birthday(birthday)
                .residentRegistrationNumber(residentRegistrationNumber)
                .baasMemberId(baasMemberId)
                .build();
    }
}
