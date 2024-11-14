package com.wekids.backend.accountTransaction.repository;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction,Long> {

    Slice<AccountTransaction> findSliceBySenderOrReceiverAndCreatedAtBetweenAndType(
            Pageable limit, String sender, String receiver, LocalDateTime startTime, LocalDateTime endTime, TransactionType type);
}
