package com.wekids.backend.design.repository;

import com.wekids.backend.design.domain.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface DesignRepository extends JpaRepository<Design, Long> {


    Optional<Design> findByMemberId(Long memberId);
}
