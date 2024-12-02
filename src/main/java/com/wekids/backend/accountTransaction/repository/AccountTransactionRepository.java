package com.wekids.backend.accountTransaction.repository;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountTransactionRepository
        extends JpaRepository<AccountTransaction,Long>, AccountTransactionCustomRepository {
}
