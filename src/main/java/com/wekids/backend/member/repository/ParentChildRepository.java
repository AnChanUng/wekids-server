package com.wekids.backend.member.repository;

import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.mapping.ParentChild;
import com.wekids.backend.member.domain.mapping.ParentChildId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParentChildRepository extends JpaRepository<ParentChild, ParentChildId> {
    Optional<ParentChild> findParentChildByChild(Child child);

    @Query("SELECT pc.child FROM ParentChild pc WHERE pc.parent.id = :parentId")
    List<Member> findChildrenByParentId(@Param("parentId") Long parentId);

    @Query("SELECT pc.parent FROM ParentChild pc WHERE pc.child.id = :childId")
    List<Member> findParentsByChildId(@Param("childId") Long childId);
}
