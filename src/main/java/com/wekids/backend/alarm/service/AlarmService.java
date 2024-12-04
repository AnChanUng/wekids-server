package com.wekids.backend.alarm.service;

import com.wekids.backend.alarm.dto.response.AlarmGetResponse;

import java.util.List;

public interface AlarmService {
    List<AlarmGetResponse> getAlarmList(Long memberId);
}
