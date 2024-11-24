package com.wekids.backend.member.repository;

import com.wekids.backend.member.domain.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    @Query("select p from Child c join ParentChild pc on c = pc.child join Parent p on pc.parent = p where c.id = :childId")
    Optional<Parent> findParentByChildId(@Param("childId") Long childId);
}
