package com.wekids.backend.card.repository;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByAccount(Account account);
}
