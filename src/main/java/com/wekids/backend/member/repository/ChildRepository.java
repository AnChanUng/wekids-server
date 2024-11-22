package com.wekids.backend.member.repository;

import com.wekids.backend.member.domain.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    Optional<Child> findChildByBankMemberId(Long bankMemberId);
}
