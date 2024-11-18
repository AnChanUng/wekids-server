package com.wekids.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ErrorCode {
    INVALID_INPUT(BAD_REQUEST, "잘못된 입력 값입니다."),
    MEMBER_NOT_FOUND(NOT_FOUND, "회원을 찾을 수 없습니다."),
    TRANSACTION_NOT_FOUND(NOT_FOUND, "거래 내역을 찾을 수 없습니다."),
    ACCOUNT_NOT_FOUND(NOT_FOUND, "계좌 정보를 찾을 수 없습니다."),
    DESIGN_NOT_FOUND(NOT_FOUND, "디자인 정보를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
