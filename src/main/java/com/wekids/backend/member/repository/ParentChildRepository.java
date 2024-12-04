package com.wekids.backend.member.repository;

import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.mapping.ParentChild;
import com.wekids.backend.member.domain.mapping.ParentChildId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentChildRepository extends JpaRepository<ParentChild, ParentChildId> {
    Optional<ParentChild> findParentChildByChild(Child child);
    Optional<ParentChild> findParentChildByParent(Parent parent);
}
