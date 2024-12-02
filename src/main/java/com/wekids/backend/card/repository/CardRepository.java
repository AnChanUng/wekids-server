package com.wekids.backend.card.repository;

import com.wekids.backend.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
