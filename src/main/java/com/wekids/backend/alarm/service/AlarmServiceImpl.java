package com.wekids.backend.alarm.service;

import com.wekids.backend.alarm.domain.Alarm;
import com.wekids.backend.alarm.dto.response.AlarmGetResponse;
import com.wekids.backend.alarm.dto.response.NewAlarmCountResponse;
import com.wekids.backend.alarm.repository.AlarmRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AlarmServiceImpl implements AlarmService{
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<AlarmGetResponse> getAlarmList(Long memberId) {
        Member member = getMember(memberId);
        List<Alarm> alarms = alarmRepository.findByMemberOrderByCreatedAtDesc(member);
        return AlarmGetResponse.from(alarms);
    }

    @Override
    @Transactional
    public void checkAlarm(Long alarmId) {
        Alarm alarm = getAlarm(alarmId);
        alarm.check();
    }

    @Override
    public NewAlarmCountResponse getNewAlarmCount(Long memberId) {
        Member member = getMember(memberId);
        Long count = alarmRepository.countAlarmsByIsCheckedFalseAndMember(member);
        return NewAlarmCountResponse.of(count);
    }

    private Alarm getAlarm(Long alarmId) {
        return alarmRepository.findById(alarmId).orElseThrow(() -> new WekidsException(ErrorCode.ALARM_NOT_FOUND, "알림 아이디: " + alarmId));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "회원 아이디: " + memberId));
    }
}
