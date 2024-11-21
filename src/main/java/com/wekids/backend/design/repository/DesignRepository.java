package com.wekids.backend.design.repository;

import com.wekids.backend.design.domain.Design;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesignRepository extends JpaRepository<Design, Long> {
    Optional<Design> findByMemberId(Long memberId);
}
