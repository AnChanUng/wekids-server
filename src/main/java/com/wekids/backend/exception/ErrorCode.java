package com.wekids.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    INVALID_INPUT(BAD_REQUEST, "잘못된 입력 값입니다."),
    MEMBER_NOT_FOUND(INTERNAL_SERVER_ERROR, "회원을 찾을 수 없습니다."),
    TRANSACTION_NOT_FOUND(INTERNAL_SERVER_ERROR, "거래 내역을 찾을 수 없습니다."),
    INVALID_TRANSACTION_AMOUNT(INTERNAL_SERVER_ERROR, "거래 할 수 없는 금액입니다."),
    INVALID_ACCOUNT_NUMBER(INTERNAL_SERVER_ERROR, "계좌번호를 찾을 수 없습니다."),
    ACCOUNT_NOT_ACTIVE(INTERNAL_SERVER_ERROR, "정지된 계좌입니다."),
    ACCOUNT_NOT_FOUND(INTERNAL_SERVER_ERROR, "계좌를 찾을 수 없습니다."),
    BAAS_NOT_RESPONSE(GATEWAY_TIMEOUT, "BaaS 응답이 없습니다."),
    BAAS_REQUEST_FAILED(INTERNAL_SERVER_ERROR, "BaaS 요청에 실패했습니다."),
    DESIGN_NOT_FOUND(INTERNAL_SERVER_ERROR, "디자인 정보를 찾을 수 없습니다."),
    INVALID_CARD_STATE(INTERNAL_SERVER_ERROR, "잘못된 카드 상태입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
