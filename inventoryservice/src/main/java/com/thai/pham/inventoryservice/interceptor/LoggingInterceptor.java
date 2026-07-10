package com.thai.pham.inventoryservice.interceptor;

import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import java.lang.Throwable;
import java.lang.System;

public class LoggingInterceptor extends AbstractMonitoringInterceptor {
    public LoggingInterceptor() {}

    @Override
    public Object invokeUnderTrace(MethodInvocation invocation, Log log) throws Throwable {
        String name = createInvocationTraceName(invocation);
        long start = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            log.info("Method " + name + " execution last:" + time + "ms");
            if(time > 10) {
                log.warn("Method execution longer than 10 ms!");
            }
        }
    }
}