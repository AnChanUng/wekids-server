package com.wekids.backend.auth.dto.request;

import com.wekids.backend.auth.enums.MemberType;

import java.time.LocalDate;

import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.enums.CardState;
import com.wekids.backend.member.domain.enums.MemberState;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SignUpRequest {

    @NotNull(message = "생일은 필수 입력 항목입니다.")
    @Past(message = "생일은 과거 날짜여야 합니다.")
    private LocalDate birthday;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(max = 50, message = "이름은 최대 50자까지 입력 가능합니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호는 '010-1234-5678' 형식으로 입력해야 합니다.")
    private String phone;

    @NotBlank(message = "간단 비밀번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^.{6}$", message = "비밀번호는 반드시 6자로 입력해야 합니다.")
    private String simplePassword;

    @NotBlank(message = "소셜 정보는 필수 입력 항목입니다.")
    private String social;

    @NotNull(message = "회원 유형은 필수 입력 항목입니다.")
    private MemberType memberType;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    @Size(max = 50, message = "법정대리인의 이름은 최대 50자까지 입력 가능합니다.")
    private String guardianName;

    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "법정대리인의 전화번호는 '010-1234-5678' 형식으로 입력해야 합니다.")
    private String guardianPhone;

    @Past(message = "법정대리인의 생일은 과거 날짜여야 합니다.")
    private LocalDate guardianBirthday;

    public Parent toParent(String simplePassword, SignUpRequest request){
        return Parent.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .cardState(CardState.NONE)
                .birthday(request.getBirthday())
                .name(request.getName())
                .simplePassword(simplePassword)
                .role("ROLE_PARENT")
                .social(request.getSocial())
                .state(MemberState.ACTIVE)
                .build();
    }

    public Child toChild(String simplePassword, SignUpRequest request){
        return Child.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .cardState(CardState.NONE)
                .birthday(request.getBirthday())
                .name(request.getName())
                .simplePassword(simplePassword)
                .role("ROLE_CHILD")
                .social(request.getSocial())
                .state(MemberState.ACTIVE)
                .build();
    }
}
