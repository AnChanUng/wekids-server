package com.wekids.backend.mission.dto.request;

import com.wekids.backend.mission.domain.enums.MissionCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MissionCreateRequest {
    @NotBlank
    private String title;
    private String content;
    @NotNull
    private LocalDate deadline;
    @NotNull
    @Positive
    private Long amount;
    @NotNull
    private MissionCategory category;
    @NotNull
    @Positive
    private Long childId;
}
