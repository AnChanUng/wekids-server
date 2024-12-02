package com.wekids.backend.mission.domain;

import com.wekids.backend.common.entity.BaseTime;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.mission.domain.enums.MissionCategory;
import com.wekids.backend.mission.domain.enums.MissionState;
import com.wekids.backend.mission.dto.request.MissionCreateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Mission extends BaseTime {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MissionCategory category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MissionState state;

    private String image;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    public static Mission createNewMission(MissionCreateRequest request, Parent parent, Child child) {
        return Mission.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .deadline(request.getDeadline())
                .amount(BigDecimal.valueOf(request.getAmount()))
                .category(request.getCategory())
                .state(MissionState.NEW)
                .parent(parent)
                .child(child)
                .build();
    }
}
