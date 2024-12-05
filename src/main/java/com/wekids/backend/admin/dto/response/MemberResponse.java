package com.wekids.backend.admin.dto.response;

import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.domain.enums.MemberState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private Long memberId;
    private String name;
    private String phone;
    private LocalDate birthday;
    private String profile;
    private String email;
    private String role;
    private MemberState memberState;
    private LocalDateTime inactiveDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .phone(member.getPhone())
                .birthday(member.getBirthday())
                .profile(member.getProfile())
                .email(member.getEmail())
                .role(member.getRole())
                .memberState(member.getState())
                .inactiveDate(member.getInactiveDate())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}
