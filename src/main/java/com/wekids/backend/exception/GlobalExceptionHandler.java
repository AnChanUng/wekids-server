package com.wekids.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.wekids.backend.exception.ErrorCode.INVALID_INPUT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WekidsException.class)
    public ResponseEntity<ErrorResponse> handleWeKidsException(WekidsException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, exception.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    List<String> ENUM_CLASSES =
            List.of("Name", "Course", "State", "StatisticsType", "DefaultSubject");
    String TYPE_MISMATCH_MESSAGE = "%s의 입력 값으로 %s는 type이 맞지 않습니다. %s의 type은 %s여야 합니다.";

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        String errorMessage = "요청 본문의 형식이 올바르지 않습니다. " +
                "JSON 형식을 확인하거나 필드 값의 타입을 확인해주세요.";

        Throwable cause = exception.getCause();
        if (cause != null && cause instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException typeMismatchException = (MethodArgumentTypeMismatchException) cause;
            String requiredType = getRequiredType(typeMismatchException);
            String propertyName = typeMismatchException.getPropertyName();
            Object value = typeMismatchException.getValue();
            errorMessage = String.format(
                    TYPE_MISMATCH_MESSAGE,
                    propertyName,
                    value,
                    propertyName,
                    requiredType
            );
        }

        return ResponseEntity.status(BAD_REQUEST)
                .body(ErrorResponse.of(
                        BAD_REQUEST.value(),
                        errorMessage
                ));
    }

    private String getRequiredType(MethodArgumentTypeMismatchException exception) {
        String requiredType = exception.getRequiredType().getSimpleName();
        if (!ENUM_CLASSES.contains(requiredType)) {
            return requiredType;
        }
        return Arrays.stream(exception.getRequiredType().getFields())
                .map(Field::getName)
                .collect(Collectors.joining(", "));
    }

    private static final String METHOD_NOT_SUPPORTED_FORMAT =
            "요청 HTTP METHOD는 <%s>이지만, 해당 URI를 지원하는 HTTP METHOD는 <%s>입니다.";

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleNotSupportedHttpMethodException(
            HttpRequestMethodNotSupportedException exception) {
        String supportedMethods = String.join(", ", exception.getSupportedMethods());
        return ResponseEntity.status(METHOD_NOT_ALLOWED)
                .body(
                        ErrorResponse.of(
                                METHOD_NOT_ALLOWED.value(),
                                String.format(
                                        METHOD_NOT_SUPPORTED_FORMAT,
                                        exception.getMethod(),
                                        supportedMethods)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidException(
            MethodArgumentNotValidException exception) {
        return ResponseEntity.status(INVALID_INPUT.getStatus())
                .body(ErrorResponse.of(exception.getBindingResult().getFieldErrors(), INVALID_INPUT));
    }
}
