package com.wekids.backend.accountTransaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wekids.backend.accountTransaction.dto.request.TransactionRequest;
import com.wekids.backend.accountTransaction.service.AccountTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class AccountTransactionControllerTest {
    @Mock
    private AccountTransactionService accountTransactionService;

    @InjectMocks
    private AccountTransactionController accountTransactionController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountTransactionController).build();
    }

    @Test
    public void 계좌_이체_성공() throws Exception {
        // 테스트 데이터 준비
        TransactionRequest request = TransactionRequest.builder()
                .parentAccountNumber("parentAcc")
                .childAccountNumber("childAcc")
                .amount(BigDecimal.valueOf(10))
                .sender("parentAcc")
                .receiver("childAcc")
                .build();

        // POST 요청에 대한 서비스 호출 검증
        mockMvc.perform(post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNoContent());

        //부모 자식 한번씩 하는거기에 1번 호출되는게 맞
        verify(accountTransactionService, times(1)).saveTransaction(refEq(request));
    }
}