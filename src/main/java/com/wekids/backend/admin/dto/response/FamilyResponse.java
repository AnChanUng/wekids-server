package com.wekids.backend.admin.dto.response;

import com.wekids.backend.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyResponse {
    private String name;
    private String phone;
    private String role;

    public static FamilyResponse from(Member member) {
        return FamilyResponse.builder()
                .name(member.getName())
                .phone(member.getPhone())
                .role(member.getRole())
                .build();
    }
}

