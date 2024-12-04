package com.wekids.backend.alarm.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NewAlarmCountResponse {
    private Long count;

    public static NewAlarmCountResponse of(Long count) {
        return new NewAlarmCountResponse(count);
    }
}
