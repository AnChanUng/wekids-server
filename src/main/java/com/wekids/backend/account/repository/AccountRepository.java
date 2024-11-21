package com.wekids.backend.account.repository;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByMember(Member member);
    @Query("SELECT a FROM Account a JOIN a.member m JOIN ParentChild c ON m.id = c.child.id WHERE c.parent.id = :parentId")
    List<Account> findAccountsByParentId(@Param("parentId") Long parentId);

}
