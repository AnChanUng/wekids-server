package com.wekids.backend.mission.service;

import com.wekids.backend.mission.dto.request.MissionAcceptRequest;
import com.wekids.backend.mission.dto.request.MissionCreateRequest;
import com.wekids.backend.mission.dto.request.MissionListGetRequestParams;
import com.wekids.backend.mission.dto.request.MissionSubmitRequest;
import com.wekids.backend.mission.dto.response.MissionGetResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MissionService {
    void createMission(MissionCreateRequest request, Long memberId);

    List<MissionGetResponse> getMissionList(MissionListGetRequestParams params, Long memberId, String role);

    MissionGetResponse getMissionDetail(Long missionId, Long memberId, String role);

    void submitMission(MissionSubmitRequest request, MultipartFile image, Long missionId, Long memberId);

    void acceptMission(MissionAcceptRequest request, Long missionId, Long memberId);

    void deleteMission(Long missionId, Long memberId);
}
