package com.example.infra.command.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import com.example.infra.command.common.CommandResponse;
import com.example.infra.command.common.Status;

@Slf4j
@Aspect
public class ThrowsExceptionAspect {
    @Around("@annotation(com.example.infra.command.advice.ThrowsException)")
    public Object handleException(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            log.error("{} failed with {}", joinPoint.getSignature(), ex.getMessage());
            return new CommandResponse<>(Status.FAILED, ex.getMessage());
        }
    }
}
