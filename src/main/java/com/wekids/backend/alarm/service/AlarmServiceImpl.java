package com.wekids.backend.alarm.service;

import com.wekids.backend.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AlarmServiceImpl implements AlarmService{
    private final AlarmRepository alarmRepository;
}
