package com.wekids.backend.alarm.repository;

import com.wekids.backend.alarm.domain.Alarm;
import com.wekids.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findByMemberOrderByCreatedAtDesc(Member member);
    Long countAlarmsByIsCheckedFalseAndMember(Member member);
}
