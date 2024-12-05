package com.wekids.backend.admin.dto.request;

import com.wekids.backend.member.domain.enums.MemberState;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class MemberSimplePasswordRequest {
    @Pattern(regexp = "^.{6}$", message = "비밀번호는 반드시 6자로 입력해야 합니다.")
    private String newPassword;
}
