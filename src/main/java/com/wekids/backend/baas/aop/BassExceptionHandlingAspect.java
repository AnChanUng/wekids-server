package com.wekids.backend.baas.aop;

import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

@Aspect
@Component
public class BassExceptionHandlingAspect {
    @Around("@annotation(com.wekids.backend.baas.aop.BassLogAndHandleException)")
    public Object logAndHandleException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            String errorMessage = "BaaS 요청 실패: " + e.getMessage();
            throw new WekidsException(ErrorCode.BAAS_REQUEST_FAILED, errorMessage);
        } catch (RestClientException e) {
            String errorMessage = "BaaS 요청 실패: 네트워크 오류 또는 알 수 없는 문제 - " + e.getMessage();
            throw new WekidsException(ErrorCode.BAAS_REQUEST_FAILED, errorMessage);
        }
    }
}
