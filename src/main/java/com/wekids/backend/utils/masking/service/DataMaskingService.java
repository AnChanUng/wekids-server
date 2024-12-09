package com.wekids.backend.utils.masking.service;

import com.wekids.backend.utils.masking.strategy.DataMaskingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DataMaskingService {
    private final DataMaskingStrategy maskingStrategy;

    public String maskData(String data) {
        return maskingStrategy.mask(data);
    }

    public String maskBalance(BigDecimal balance) {
        if (balance == null) return null;

        String balanceStr = balance.toPlainString();
        int length = balanceStr.length();

        if (length <= 2) return "0";
        return balanceStr.substring(0, 2) + "*".repeat(length - 2);
    }

}