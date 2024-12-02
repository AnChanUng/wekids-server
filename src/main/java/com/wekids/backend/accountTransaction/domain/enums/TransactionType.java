package com.wekids.backend.accountTransaction.domain.enums;

public enum TransactionType {
    DEPOSIT, WITHDRAWAL;

    public static TransactionType from(String value) {
        if(value.equals("입금") || value.equals("DEPOSIT")){
            return TransactionType.DEPOSIT;
        }

        return TransactionType.WITHDRAWAL;
    }
}
