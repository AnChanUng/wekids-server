package com.wekids.backend.member.repository;

import com.wekids.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsAllByEmail(String email);
    Optional<Member> findByNameAndPhone(String name, String phone);

    @Query("SELECT m.bankMemberId FROM Card c JOIN c.account a JOIN a.member m where c.id = :cardId")
    Optional<Long> findBankMemberIdByCardId(@Param("cardId") Long cardId);

    @Query("SELECT m.bankMemberId FROM Account a JOIN a.member m where a.id = :accountId")
    Optional<Long> findBankMemberIdByAccountId(@Param("accountId") Long accountId);
}
