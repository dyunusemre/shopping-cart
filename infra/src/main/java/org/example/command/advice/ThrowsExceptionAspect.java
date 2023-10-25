package org.example.command.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.command.common.CommandResponse;
import org.example.command.common.Status;

@Slf4j
@Aspect
public class ThrowsExceptionAspect {
    @Around("@annotation(org.example.command.advice.ThrowsException)")
    public Object handleException(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            log.error("{} failed with {}", joinPoint.getSignature(), ex.getMessage());
            return new CommandResponse<>(Status.FAILED, ex.getMessage());
        }
    }
}
