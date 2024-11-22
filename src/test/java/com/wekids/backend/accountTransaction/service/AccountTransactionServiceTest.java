package com.wekids.backend.accountTransaction.service;

import com.wekids.backend.account.domain.Account;
import com.wekids.backend.account.repository.AccountRepository;
import com.wekids.backend.accountTransaction.domain.AccountTransaction;
import com.wekids.backend.accountTransaction.dto.response.TransactionGetResponse;
import com.wekids.backend.accountTransaction.dto.response.TransactionListResponse;
import com.wekids.backend.member.domain.Member;
import com.wekids.backend.support.fixture.AccountFixture;
import com.wekids.backend.support.fixture.AccountTransactionFixture;
import com.wekids.backend.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.backend.support.fixture.ChildFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountTransactionServiceTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock private AccountRepository accountRepository;
    @InjectMocks private AccountTransactionServiceImpl accountTransactionService;


    @Test
    void 거래_정보를_필터링하여_조회한다() {
        long accountId = 1L;


        TransactionGetResponse transaction1 = TransactionGetResponse.builder()
                .title("카카오페이")
                .type("DEPOSIT")
                .amount(1000L)
                .balance(19862L)
                .sender("조예은")
                .receiver("강현우")
                .currencyCode("원")
                .transactionDate(LocalDateTime.now())
                .build();

        TransactionGetResponse transaction2 = TransactionGetResponse.builder()
                .title("카카오페이")
                .type("DEPOSIT")
                .amount(1000L)
                .balance(19862L)
                .sender("조예은")
                .receiver("강현우")
                .currencyCode("원")
                .transactionDate(LocalDateTime.now())
                .build();

        List<TransactionGetResponse> response = Arrays.asList(transaction1, transaction2);


        when(accountRepository.findById(accountId)).thenReturn(Optional.of(new AccountFixture().build()));


        List<TransactionGetResponse> filteredTransactions = response.stream()
                .filter(tx -> tx.getType().equalsIgnoreCase("DEPOSIT"))
                .collect(Collectors.toList());


        Pageable pageable = PageRequest.of(0, 10);
        int fromIndex = (int) pageable.getOffset();
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), filteredTransactions.size());
        boolean hasNext = toIndex < filteredTransactions.size();

        Slice<TransactionGetResponse> result = new SliceImpl<>(
                filteredTransactions.subList(fromIndex, toIndex),
                pageable,
                hasNext
        );


        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getType()).isEqualTo("DEPOSIT");
        assertThat(accountTransactionService.showTransactionList(accountId, null, null, "DEPOSIT", 0, 5).getContent().get(0).getType()).isEqualTo(result.getContent().get(0).getType());

    }


}
