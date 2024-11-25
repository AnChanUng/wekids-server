package com.wekids.backend.design.repository;

import com.wekids.backend.design.domain.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DesignRepository extends JpaRepository<Design, Long> {

}