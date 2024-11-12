package com.wekids.backend.child.repository;

import com.wekids.backend.member.domain.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    @Query("SELECT c FROM Child c JOIN FETCH ParentChild pc ON c.id = pc.child.id WHERE pc.parent.id = :parentId")
    List<Child> findChildrenByParentId(@Param("parentId") Long parentId);
}
