package com.wekids.backend.mission.service;

import com.wekids.backend.mission.dto.request.MissionCreateRequest;
import com.wekids.backend.mission.dto.request.MissionListGetRequestParams;
import com.wekids.backend.mission.dto.response.MissionGetResponse;
import com.wekids.backend.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService{
    private final MissionRepository missionRepository;

    @Override
    public void createMission(MissionCreateRequest request, Long memberId) {

    }

    @Override
    public List<MissionGetResponse> getMissionList(MissionListGetRequestParams params, Long memberId) {
        return null;
    }

    @Override
    public MissionGetResponse getMissionDetail(Long missionId, Long memberId) {
        return null;
    }

    @Override
    public void submitMission(Long missionId, Long memberId) {

    }

    @Override
    public void acceptMission(Long missionId, Long memberId) {

    }

    @Override
    public void deleteMission(Long missionId, Long memberId) {

    }
}
