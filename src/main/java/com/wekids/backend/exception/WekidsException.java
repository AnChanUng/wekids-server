package com.wekids.backend.exception;

import lombok.Getter;

@Getter
public class WekidsException extends RuntimeException {

    private final ErrorCode errorCode;

    public WekidsException(final ErrorCode errorCode, String details) {
        super(details);
        this.errorCode = errorCode;
    }
}
