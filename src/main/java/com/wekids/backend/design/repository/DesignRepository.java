package com.wekids.backend.design.repository;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.design.domain.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DesignRepository extends JpaRepository<Design, Long> {
    Optional<Design> findByAccount(Account account);
}
