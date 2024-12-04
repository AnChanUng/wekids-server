package com.wekids.backend.alarm.service;

import com.wekids.backend.alarm.dto.response.AlarmGetResponse;
import com.wekids.backend.alarm.dto.response.NewAlarmCountResponse;

import java.util.List;

public interface AlarmService {
    List<AlarmGetResponse> getAlarmList(Long memberId);

    void checkAlarm(Long alarmId);

    NewAlarmCountResponse getNewAlarmCount(Long memberId);
}
