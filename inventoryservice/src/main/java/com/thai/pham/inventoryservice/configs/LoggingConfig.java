package com.thai.pham.inventoryservice.configs;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.thai.pham.inventoryservice.interceptor.LoggingInterceptor;

@Configuration
@Aspect
@EnableAspectJAutoProxy
class LoggingConfig {
    @Pointcut("execution(* com.thai.pham.inventoryservice.service.ProductService.*(..))")
    public void productServiceFunctionMonitor() {}

    @Bean
    public LoggingInterceptor createMethodExecutionTimeMeasurer() {
        return LoggingInterceptor();
    }

    @Bean
    public Advisor createMethodExecutionTimeMeasurerAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("com.thai.pham.inventoryservice.configs.LoggingConfig.productServiceFunctionMonitor()");
        return new DefaultPointcutAdvisor(pointcut, createMethodExecutionTimeMeasurer());
    }
}