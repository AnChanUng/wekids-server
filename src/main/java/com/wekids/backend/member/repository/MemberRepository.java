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

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT p FROM Parent p WHERE p.id = :id")
    Optional<Parent> findByIdAndMemberType(@Param("id") Long id);

    @Query(value = "SELECT c.* FROM member c " +
            "JOIN parent_child pc ON c.id = pc.child_id " +
            "WHERE pc.parent_id = :parentId", nativeQuery = true)
    List<Child> findChildrenByParentId(@Param("parentId") Long parentId);

}
