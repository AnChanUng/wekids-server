package com.wekids.backend.member.repository;

import com.wekids.backend.member.domain.mapping.ParentChild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentChildRepository extends JpaRepository<ParentChild, Long> {
}
