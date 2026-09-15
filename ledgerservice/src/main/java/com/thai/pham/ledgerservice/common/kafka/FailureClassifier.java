package com.thai.pham.ledgerservice.common.kafka;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class FailureClassifier {
    public boolean isRetryable(Throwable exception) {
        return switch(exception) {
            case ConstraintViolationException c -> false;
            case NonRetryableException n -> false;
            case TransientDataAccessException t -> true;
            case RetryableException -> true;
            default -> false;
        }
    }
}