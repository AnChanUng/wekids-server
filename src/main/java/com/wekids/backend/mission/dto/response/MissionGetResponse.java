package com.wekids.backend.mission.dto.response;

import com.wekids.backend.member.domain.Child;
import com.wekids.backend.mission.domain.Mission;
import com.wekids.backend.mission.domain.enums.MissionCategory;
import com.wekids.backend.mission.domain.enums.MissionState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class MissionGetResponse {
    private Long missionId;
    private String title;
    private String content;
    private MissionState state;
    private LocalDate deadline;
    private MissionCategory category;
    private String childName;
    private String childProfile;
    private String image;
    private String memo;

    public static MissionGetResponse from(Mission mission) {
        Child child = mission.getChild();

        return MissionGetResponse.builder()
                .missionId(mission.getId())
                .title(mission.getTitle())
                .content(mission.getContent())
                .state(mission.getState())
                .deadline(mission.getDeadline())
                .category(mission.getCategory())
                .childName(child.getName())
                .childProfile(child.getProfile())
                .image(mission.getImage())
                .memo(mission.getMemo())
                .build();
    }

    public static List<MissionGetResponse> from(List<Mission> missions) {
        return missions.stream()
                .map(mission -> from(mission))
                .collect(Collectors.toList());
    }
}
