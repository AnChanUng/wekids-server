package com.wekids.backend.alarm.domain;

import com.wekids.backend.alarm.domain.enums.AlarmTargetState;
import com.wekids.backend.alarm.domain.enums.AlarmType;
import com.wekids.backend.common.entity.BaseTime;
import com.wekids.backend.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlarmType type;

    private Long targetId;

    @Enumerated(EnumType.STRING)
    private AlarmTargetState targetState;

    @Column(nullable = false)
    private Boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
