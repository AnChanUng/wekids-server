package com.wekids.backend.mission.repository;

import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.repository.ChildRepository;
import com.wekids.backend.member.repository.ParentRepository;
import com.wekids.backend.mission.domain.Mission;
import com.wekids.backend.mission.domain.enums.MissionCategory;
import com.wekids.backend.mission.domain.enums.MissionState;
import com.wekids.backend.support.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class MissionRepositoryTest {
    @Autowired
    MissionRepository missionRepository;
    @Autowired
    ParentRepository parentRepository;
    @Autowired
    ChildRepository childRepository;

    @Test
    void findByCondition() {
        Parent parent = parentRepository.findById(1L).get();
        Child child = childRepository.findById(2L).get();
        MissionState state = MissionState.NEW;
        MissionCategory category = MissionCategory.HOUSE_WORK;

        List<Mission> allByCondition = missionRepository.findAllByCondition(state, category, parent, child);
        System.out.println("allByCondition = " + allByCondition);
    }

}