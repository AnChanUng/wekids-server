package com.wekids.backend.mission.repository;

import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.mission.domain.Mission;
import com.wekids.backend.mission.domain.enums.MissionCategory;
import com.wekids.backend.mission.domain.enums.MissionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    @Query("select m from Mission m join fetch m.child c where (:state is null or m.state = :state) and (:category is null or m.category = :category) and (:parent is null or m.parent = :parent) and (:child is null or c = :child)")
    List<Mission> findAllByCondition(@Param("state") MissionState state, @Param("category") MissionCategory category, @Param("parent") Parent parent, @Param("child") Child child);
}
