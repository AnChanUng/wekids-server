package com.wekids.backend.mission.service;

import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.mapping.ParentChildId;
import com.wekids.backend.member.repository.ChildRepository;
import com.wekids.backend.member.repository.ParentChildRepository;
import com.wekids.backend.member.repository.ParentRepository;
import com.wekids.backend.mission.domain.Mission;
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
public class MissionServiceImpl implements MissionService {
    private final MissionRepository missionRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final ParentChildRepository parentChildRepository;

    @Override
    @Transactional
    public void createMission(MissionCreateRequest request, Long memberId) {
        Parent parent = getParent(memberId);
        Child child = getChild(request.getChildId());

        validateMemberRelationship(parent.getId(), child.getId());

        Mission mission = Mission.createNewMission(request, parent, child);
        missionRepository.save(mission);
    }

    private void validateMemberRelationship(Long parentId, Long childId) {
        ParentChildId parentChildId = ParentChildId.of(parentId, childId);
        boolean checkRelationship = parentChildRepository.existsById(parentChildId);
        if (!checkRelationship)
            throw new WekidsException(ErrorCode.INVALID_MEMBER_RELATIONSHIP, "부모 회원 아이디: " + parentId + " 자식 회원 아이디: " + childId);
    }

    private Child getChild(Long childId) {
        return childRepository.findById(childId).orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "자식 회원 아이디: " + childId));
    }

    private Parent getParent(Long parentId) {
        return parentRepository.findById(parentId).orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "부모 회원 아이디: " + parentId));
    }

    @Override
    public List<MissionGetResponse> getMissionList(MissionListGetRequestParams params, Long memberId, String role) {
        Parent parent = null;
        Child child = null;

        if (role.contains("PARENT")) {
            parent = getParent(memberId);
            if(params.getChild() != null) child = getChild(params.getChild());
        }

        if (role.contains("CHILD")) {
            child = getChild(memberId);
        }

        List<Mission> missionList = missionRepository.findAllByCondition(params.getState(), params.getCategory(), parent, child);
        return MissionGetResponse.from(missionList);
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
