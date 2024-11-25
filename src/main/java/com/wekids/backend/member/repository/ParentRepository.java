package com.wekids.backend.member.repository;

import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.domain.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    @Query("SELECT c FROM Child c JOIN FETCH ParentChild pc ON c.id = pc.child.id WHERE pc.parent.id = :parentId")
    List<Child> findChildrenByParentId(@Param("parentId") Long parentId);

}
