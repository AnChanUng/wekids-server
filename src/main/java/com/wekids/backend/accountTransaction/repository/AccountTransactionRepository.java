package com.wekids.backend.accountTransaction.repository;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction,Long> {

AccountTransaction findByTransactionId(Long transactionId);

}
