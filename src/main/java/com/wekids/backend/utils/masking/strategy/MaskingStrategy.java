package com.wekids.backend.utils.masking.strategy;

public class MaskingStrategy implements DataMaskingStrategy {
    @Override
    public String mask(String data) {
        if (data == null || data.isEmpty()) return data;

        if (data.matches("^[가-힣]{2,}$")) {
            return data.substring(0, 1) + "*".repeat(data.length() - 1);
        }

        if (data.matches("\\d{12,16}")) {
            return "***********" + data.substring(data.length() - 4);
        }

        return data.replaceAll(".(?=.{4})", "*");
    }
}