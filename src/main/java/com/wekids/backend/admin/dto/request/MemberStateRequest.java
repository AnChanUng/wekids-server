package com.wekids.backend.admin.dto.request;

import com.wekids.backend.account.domain.enums.AccountState;
import com.wekids.backend.member.domain.enums.MemberState;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberStateRequest {
    @NotNull(message = "Member state is required.")
    private MemberState state;
}
