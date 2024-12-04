package com.wekids.backend.baas.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WekidsRegistrationRequest {
    private String name;
    private LocalDate birthday;
    private String residentRegistrationNumber;
    @Builder.Default
    private Long baasMemberId = 1L;

    public static WekidsRegistrationRequest of(String name, LocalDate birthday, String residentRegistrationNumber) {
        return WekidsRegistrationRequest.builder()
                .name(name)
                .birthday(birthday)
                .residentRegistrationNumber(residentRegistrationNumber)
                .build();
    }
}