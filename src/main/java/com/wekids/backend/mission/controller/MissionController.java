package com.wekids.backend.mission.controller;

import com.wekids.backend.auth.controller.MemberId;
import com.wekids.backend.auth.controller.Role;
import com.wekids.backend.mission.dto.request.MissionAcceptRequest;
import com.wekids.backend.mission.dto.request.MissionCreateRequest;
import com.wekids.backend.mission.dto.request.MissionListGetRequestParams;
import com.wekids.backend.mission.dto.request.MissionSubmitRequest;
import com.wekids.backend.mission.dto.response.MissionGetResponse;
import com.wekids.backend.mission.service.MissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/missions")
@RequiredArgsConstructor
public class MissionController {
    private final MissionService missionService;

    @PostMapping
    public ResponseEntity<Void> createMission(@RequestBody @Valid  MissionCreateRequest request, @MemberId Long memberId) {
        missionService.createMission(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MissionGetResponse>> showMissionList(@ModelAttribute @Valid MissionListGetRequestParams params, @MemberId Long memberId, @Role String role) {
        List<MissionGetResponse> response = missionService.getMissionList(params, memberId, role);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{missionId}")
    public ResponseEntity<MissionGetResponse> showMissionDetail(@PathVariable("missionId") Long missionId, @MemberId Long memberId, @Role String role) {
        MissionGetResponse response = missionService.getMissionDetail(missionId, memberId, role);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{missionId}/submit")
    public ResponseEntity<Void> submitMission(@RequestPart(value="data", required = false) @Valid MissionSubmitRequest request, @RequestPart(value = "image", required = false) MultipartFile image, @PathVariable("missionId") Long missionId) {
        missionService.submitMission(request, image, missionId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{missionId}/accept")
    public ResponseEntity<Void> acceptMission(@RequestBody @Valid MissionAcceptRequest request, @PathVariable("missionId") Long missionId) {
        missionService.acceptMission(request, missionId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{missionId}/cancel")
    public ResponseEntity<Void> cancelMission(@PathVariable("missionId") Long missionId) {
        missionService.cancelMission(missionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{missionId}")
    public ResponseEntity<Void> deleteMission(@PathVariable("missionId") Long missionId) {
        missionService.deleteMission(missionId);
        return ResponseEntity.noContent().build();
    }

}
