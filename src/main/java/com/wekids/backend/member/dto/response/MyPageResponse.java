package com.wekids.backend.member.dto.response;

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
public class MyPageResponse {
    private String name;
    private String phone;
    private LocalDate birthday;
    private String profile;
    private String email;
    private String type;
    private LocalDateTime createdAt;

    public static MyPageResponse from(Member member) {
        return MyPageResponse.builder()
                .name(member.getName())
                .phone(member.getPhone())
                .birthday(member.getBirthday())
                .profile(member.getProfile())
                .email(member.getEmail())
                .type(member.getRole().substring(5))
                .createdAt(member.getCreatedAt())
                .build();
    }
}
