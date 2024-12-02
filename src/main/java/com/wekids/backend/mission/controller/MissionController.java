package com.wekids.backend.mission.controller;

import com.wekids.backend.auth.controller.MemberId;
import com.wekids.backend.auth.controller.Role;
import com.wekids.backend.mission.dto.request.MissionCreateRequest;
import com.wekids.backend.mission.dto.request.MissionListGetRequestParams;
import com.wekids.backend.mission.dto.response.MissionGetResponse;
import com.wekids.backend.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/missions")
@RequiredArgsConstructor
public class MissionController {
    private final MissionService missionService;

    @PostMapping
    public ResponseEntity<Void> createMission(@RequestBody MissionCreateRequest request, @MemberId Long memberId) {
        missionService.createMission(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MissionGetResponse>> showMissionList(@ModelAttribute MissionListGetRequestParams params, @MemberId Long memberId, @Role String role) {
        List<MissionGetResponse> response = missionService.getMissionList(params, memberId, role);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{missionId}")
    public ResponseEntity<MissionGetResponse> showMissionDetail(@PathVariable Long missionId, @MemberId Long memberId) {
        MissionGetResponse response = missionService.getMissionDetail(missionId, memberId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{missionId}/submit")
    public ResponseEntity<Void> submitMission(@PathVariable Long missionId, @MemberId Long memberId) {
        missionService.submitMission(missionId, memberId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{missionId}/accept")
    public ResponseEntity<Void> acceptMission(@PathVariable Long missionId, @MemberId Long memberId) {
        missionService.acceptMission(missionId, memberId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{missionId}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long missionId, @MemberId Long memberId) {
        missionService.deleteMission(missionId, memberId);
        return ResponseEntity.noContent().build();
    }

}
