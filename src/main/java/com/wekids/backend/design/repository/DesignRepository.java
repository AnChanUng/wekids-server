package com.wekids.backend.design.repository;

import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.domain.DesignId;
import com.wekids.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignRepository extends JpaRepository<Design, Long> {


    Design findByMemberId(Long memberId);
}
