package com.wekids.backend.alarm.controller;

import com.wekids.backend.alarm.dto.response.AlarmGetResponse;
import com.wekids.backend.alarm.dto.response.NewAlarmCountResponse;
import com.wekids.backend.alarm.service.AlarmService;
import com.wekids.backend.auth.controller.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping
    public ResponseEntity<List<AlarmGetResponse>> showAlarmList(@MemberId Long memberId) {
        List<AlarmGetResponse> response = alarmService.getAlarmList(memberId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{alarmId}/check")
    public ResponseEntity<Void> checkAlarm(@PathVariable("alarmId") Long alarmId) {
        alarmService.checkAlarm(alarmId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<NewAlarmCountResponse> getNewAlarmCount(@MemberId Long memberId) {
        NewAlarmCountResponse response = alarmService.getNewAlarmCount(memberId);
        return ResponseEntity.ok(response);
    }
}
