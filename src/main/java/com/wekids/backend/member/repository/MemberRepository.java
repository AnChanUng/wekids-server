package com.wekids.backend.member.repository;

import com.wekids.backend.member.domain.Member;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.mapping.ParentChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByBankMemberId(Long bankMemberId);

    @Query("select p from Child c join ParentChild pc on c = pc.child join Parent p on pc.parent = p where c.id = :childId")
    Optional<Parent> findParentByChildId(Long childId);
}
