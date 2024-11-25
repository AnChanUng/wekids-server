package com.wekids.backend.accountTransaction.repository;

import static com.wekids.backend.accountTransaction.domain.QAccountTransaction.*;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.domain.QAccountTransaction;
import com.wekids.backend.accountTransaction.domain.enums.TransactionType;
import com.wekids.backend.accountTransaction.dto.enums.TransactionRequestType;
import com.wekids.backend.accountTransaction.dto.response.TransactionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AccountTransactionCustomRepositoryImpl implements AccountTransactionCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<LocalDateTime> findMostRecentTransactionDateTime(
            Long accountId,
            TransactionRequestType transactionRequestType,
            LocalDateTime start,
            LocalDateTime end
    ) {
        JPQLQuery<LocalDateTime> query = jpaQueryFactory
                .select(accountTransaction.createdAt)
                .from(accountTransaction)
                .where(
                        accountTransaction.account.id.eq(accountId)
                                .and(accountTransaction.createdAt.between(start, end))
                                .and(buildTransactionTypeCondition(transactionRequestType))
                )
                .orderBy(accountTransaction.createdAt.desc())
                .limit(1);

        return Optional.ofNullable(query.fetchOne());
    }


    @Override
    public Slice<TransactionResult> findAccountTransactionByTypeSortedByTimeDesc(
            Long accountId,
            TransactionRequestType transactionRequestType,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    ) {
        JPQLQuery<TransactionResult> query = jpaQueryFactory
                .select(Projections.constructor(
                        TransactionResult.class,
                        accountTransaction.id,
                        accountTransaction.title,
                        accountTransaction.type,
                        accountTransaction.amount,
                        accountTransaction.balance,
                        accountTransaction.createdAt
                ))
                .from(accountTransaction)
                .where(
                        accountTransaction.account.id.eq(accountId)
                                .and(buildTransactionTypeCondition(transactionRequestType))
                                .and(accountTransaction.createdAt.between(start, end))
                )
                .orderBy(accountTransaction.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1);

        List<TransactionResult> results = query.fetch();

        boolean hasNext = results.size() > pageable.getPageSize();

        return new SliceImpl<>(
                hasNext ? results.subList(0, pageable.getPageSize()) : results,
                pageable,
                hasNext
        );
    }


    private BooleanExpression buildTransactionTypeCondition(TransactionRequestType transactionRequestType) {
        if (transactionRequestType == TransactionRequestType.ALL) {
            return null;
        } else if (transactionRequestType == TransactionRequestType.DEPOSIT) {
            return accountTransaction.type.eq(TransactionType.DEPOSIT);
        } else if (transactionRequestType == TransactionRequestType.WITHDRAWAL) {
            return accountTransaction.type.eq(TransactionType.WITHDRAWAL);
        }
        return null;
    }
}
