package com.wekids.backend.member.repository;

import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    @Query("select p from Child c join ParentChild pc on c = pc.child join Parent p on pc.parent = p where c.id = :childId")
    Optional<Parent> findParentByChildId(@Param("childId") Long childId);

    @Query("select p from Parent p where p.phone = :phone and p.name = :name and p.role = 'ROLE_PARENT'")
    Optional<Parent> findByPhoneAndName(@Param("phone") String phone, @Param("name")String name);
}
