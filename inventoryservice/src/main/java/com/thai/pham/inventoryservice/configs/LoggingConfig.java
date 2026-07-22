package com.thai.pham.inventoryservice.configs;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.Advisor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.thai.pham.inventoryservice.interceptor.LoggingInterceptor;

@Configuration
@EnableAspectJAutoProxy
public class LoggingConfig {
    @Bean
    public LoggingInterceptor createMethodExecutionTimeMeasurer() {
        return new LoggingInterceptor();
    }

    @Bean
    public Advisor createMethodExecutionTimeMeasurerAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.thai.pham.inventoryservice.service.ProductService.*(..))");
        return new DefaultPointcutAdvisor(pointcut, createMethodExecutionTimeMeasurer());
    }
}