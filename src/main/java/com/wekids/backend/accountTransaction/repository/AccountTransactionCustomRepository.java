package com.wekids.backend.accountTransaction.repository;

import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.dto.enums.TransactionRequestType;
import com.wekids.backend.accountTransaction.dto.response.TransactionResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountTransactionCustomRepository {
    Optional<LocalDateTime> findMostRecentTransactionDateTime(
            Long accountId,
            TransactionRequestType transactionRequestType,
            LocalDateTime start,
            LocalDateTime end
    );

    Slice<TransactionResult> findAccountTransactionByTypeSortedByTimeDesc(
            Long accountId,
            TransactionRequestType transactionRequestType,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );
}
