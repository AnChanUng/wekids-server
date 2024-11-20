package com.wekids.backend.card.repository;

import com.wekids.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardBaasRepository extends JpaRepository<Member, Long> {
}
