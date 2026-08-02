package com.thai.pham.inventoryservice.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.Throwable;
import java.lang.System;

@Aspect
@Component
public class LoggingInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Around("execution(public * com.thai.pham.inventoryservice.service.ProductService.*(..))")
    public Object invokeUnderTrace(ProceedingJoinPoint jointPoint) throws Throwable {
        String name = jointPoint.getSignature().getName();
        long start = System.currentTimeMillis();
        try {
            return jointPoint.proceed();
        } finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            log.info("Method {} execution last:{}ms", name, time);
            if(time > 10) {
                log.warn("Method execution longer than 10 ms!");
            }
        }
    }

}