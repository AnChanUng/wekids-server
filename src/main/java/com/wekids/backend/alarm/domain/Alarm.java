package com.wekids.backend.alarm.domain;

import com.wekids.backend.alarm.domain.enums.AlarmType;
import com.wekids.backend.common.entity.BaseTime;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.mission.domain.Mission;
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

    private String targetState;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isChecked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static Alarm createMissionAlarm(Mission mission, Member member) {
        return Alarm.builder()
                .type(AlarmType.MISSION)
                .targetId(mission.getId())
                .targetState(mission.getState().name())
                .member(member)
                .build();
    }

    public static Alarm createCardDesignedAlarm(Child child, Parent parent) {
        return Alarm.builder()
                .type(AlarmType.CARD)
                .targetId(child.getId())
                .targetState(child.getCardState().name())
                .member(parent)
                .build();
    }

    public static Alarm createCardCreatedAlarm(Child child) {
        return Alarm.builder()
                .type(AlarmType.CARD)
                .targetState(child.getCardState().name())
                .member(child)
                .build();
    }

    public void check() {
        this.isChecked = true;
    }
}
