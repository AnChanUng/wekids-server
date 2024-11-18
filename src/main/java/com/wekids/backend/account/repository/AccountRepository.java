package com.wekids.backend.account.repository;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByMember(Member member);
    @Query("SELECT d FROM Design d WHERE d.account.id = :account_id")
    Design findDesignByAccountId(@Param("account_id") Long accountId);
}
