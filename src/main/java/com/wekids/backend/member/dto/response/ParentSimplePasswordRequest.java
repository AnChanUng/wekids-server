package com.wekids.backend.member.dto.response;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ParentSimplePasswordRequest {
    @Pattern(regexp = "^.{6}$", message = "비밀번호는 반드시 6자로 입력해야 합니다.")
    private String rawPassword;

    @Pattern(regexp = "^.{6}$", message = "비밀번호는 반드시 6자로 입력해야 합니다.")
    private String newPassword;
}
