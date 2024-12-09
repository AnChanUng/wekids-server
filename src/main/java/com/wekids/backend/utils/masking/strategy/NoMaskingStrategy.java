package com.wekids.backend.utils.masking.strategy;

public class NoMaskingStrategy implements DataMaskingStrategy {
    @Override
    public String mask(String data) {
        return data;
    }
}
