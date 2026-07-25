package com.thai.pham.inventoryservice.configs;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.thai.pham.inventoryservice.interceptor.LoggingInterceptor;
import org.springframework.context.annotation.Role;

@Configuration
@EnableAspectJAutoProxy
public class PerformanceLoggingConfig {
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LoggingInterceptor createMethodExecutionTimeMeasurer() {
        return new LoggingInterceptor();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor createMethodExecutionTimeMeasurerAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.thai.pham.inventoryservice.service.ProductService.*(..))");
        return new DefaultPointcutAdvisor(pointcut, createMethodExecutionTimeMeasurer());
    }
}