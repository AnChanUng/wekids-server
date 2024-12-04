package com.wekids.backend.alarm.dto.response;

import com.wekids.backend.alarm.domain.Alarm;
import com.wekids.backend.alarm.domain.enums.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmGetResponse {
    private AlarmType type;
    private Long targetId;
    private String targetState;
    private Boolean isChecked;
    private LocalDateTime createdAt;

    public static AlarmGetResponse from(Alarm alarm) {
        return AlarmGetResponse.builder()
                .type(alarm.getType())
                .targetId(alarm.getTargetId())
                .targetState(alarm.getTargetState())
                .isChecked(alarm.getIsChecked())
                .createdAt(alarm.getCreatedAt())
                .build();
    }

    public static List<AlarmGetResponse> from(List<Alarm> alarms) {
        return alarms.stream()
                .map(alarm -> from(alarm))
                .collect(Collectors.toList());
    }
}
