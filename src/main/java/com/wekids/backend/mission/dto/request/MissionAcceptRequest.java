package com.wekids.backend.mission.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MissionAcceptRequest {
    @Pattern(regexp = "^\\d{6}$")
    private String simplePassword;
}
