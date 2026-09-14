package com.thai.pham.ledgerservice.common.kafka.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Set;

@Aspect
@Component
@RequiredArgsConstructor
public class KafkaMessageContentValidator {
    private final Validator validator;

    @Around("@annotation(com.thai.pham.ledgerservice.common.kafka.validation.annotation.ValidateMessageContent)")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();

        for (Object argument : arguments) {

            if (argument == null) {
                continue;
            }

            Set<ConstraintViolation<Object>> violations =
                    validator.validate(argument);

            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        }

        return joinPoint.proceed();
    }
}