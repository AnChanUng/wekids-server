package com.wekids.backend.accountTransaction.dto.enums;

public enum TransactionRequestType {
    ALL("all"),
    DEPOSIT("deposit"),
    WITHDRAWAL("withdrawal");
    private final String fieldName;

    TransactionRequestType(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName(){
        return fieldName;
    }
}
