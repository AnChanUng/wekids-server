package com.wekids.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.wekids.backend.exception.ErrorCode.INVALID_INPUT;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNotFound(NoHandlerFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(ex.getBody());
    }

    @ExceptionHandler(WekidsException.class)
    public ResponseEntity<ErrorResponse> handleWeKidsException(WekidsException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, exception.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException exception) {
        String parameterName = exception.getParameterName();
        String parameterType = exception.getParameterType();

        String errorMessage = String.format(
                "필수 요청 파라미터 '%s'가 누락되었습니다. '%s' 타입의 값을 포함해주세요.",
                parameterName,
                parameterType
        );

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(
                        "INTERNAL_SERVER_ERROR",
                        errorMessage
                ));
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        String propertyName = exception.getName();
        Object value = exception.getValue();
        String requiredType = exception.getRequiredType() != null ? exception.getRequiredType().getSimpleName() : "Unknown";

        String validValues = "Unknown";
        if (exception.getRequiredType() != null && exception.getRequiredType().isEnum()) {
            validValues = Arrays.stream(exception.getRequiredType().getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
        }

        String errorMessage = String.format(
                "%s에 '%s' 값이 전달되었습니다. 유효한 값은 [%s]이며, 타입은 '%s'여야 합니다.",
                propertyName,
                value,
                validValues,
                requiredType
        );

        return ResponseEntity.status(INVALID_INPUT.getStatus())
                .body(ErrorResponse.of(
                        "INVALID_INPUT",
                        errorMessage
                ));
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleNotSupportedHttpMethodException(
            HttpRequestMethodNotSupportedException exception) {
        String supportedMethods = String.join(", ", exception.getSupportedMethods());
        return ResponseEntity.status(METHOD_NOT_ALLOWED)
                .body(
                        ErrorResponse.of(
                                "METHOD_NOT_ALLOWED",
                                String.format(
                                        "요청 HTTP METHOD는 <%s>이지만, 해당 URI를 지원하는 HTTP METHOD는 <%s>입니다.",
                                        exception.getMethod(),
                                        supportedMethods)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidException(
            MethodArgumentNotValidException exception) {
        return ResponseEntity.status(INVALID_INPUT.getStatus())
                .body(ErrorResponse.of(exception.getBindingResult() .getFieldErrors(), INVALID_INPUT));
    }
}