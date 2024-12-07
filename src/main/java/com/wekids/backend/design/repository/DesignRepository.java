package com.wekids.backend.design.repository;

import com.wekids.backend.design.domain.Design;
import com.wekids.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface DesignRepository extends JpaRepository<Design, Long> {
    Optional<Design> findByMember(Member member);
}