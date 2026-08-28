package com.thai.pham.ledgerservice.common.idempotent.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.thai.pham.ledgerservice.common.idempotent.detail.IdempotentDetail;
import com.thai.pham.ledgerservice.common.idempotent.repository.IdempotentRecordRepository;
import com.thai.pham.ledgerservice.common.idempotent.entity.IdempotentRecord;

@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class IdempotentAspect {
    private final PlatformTransactionManager transactionManager;
    private final IdempotentRecordRepository repository;

    public IdempotentAspect(
            @Qualifier("idempotencyTransactionManager") PlatformTransactionManager transactionManager,
            IdempotencyRecordRepository repository) {
        this.transactionManager = transactionManager;
        this.repository = repository;
    }

    @Around("@annotation(idempotentConsumer)")
    public Object handleIdempotency(ProceedingJoinPoint joinPoint, IdempotentConsumer idempotentConsumer) throws Throwable {
        String key = extractKey(joinPoint);

        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);

        return txTemplate.execute(status -> {
            try {
                if (repository.existsByIdempotencyKey(key)) {
                    log.info("Message with idempotency key {} already processed, skipping.", key);
                    return null; 
                }
                IdempotentRecord record = new IdempotentRecord(key, IdempotencyStatus.PROCESSING);
                repository.saveAndFlush(record); 

                Object result = joinPoint.proceed();

                record.setStatus(IdempotencyStatus.COMPLETED);
                repository.save(record);

                return result; 

            } catch (DataIntegrityViolationException e) {
                log.warn("Race condition: idempotency key {} already exists, skipping processing.", key);
                status.setRollbackOnly();
                return null;
            }catch (Throwable e) {
                log.error("Error processing message with key {}, rolling back transaction.", key, e);
                status.setRollbackOnly();
                throw new RuntimeException("Error processing message", e);
            }
        });
    }

    private String extractKey(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(IdempotentKey.class)) {
                Object arg = args[i];  
                if (arg instanceof String) {
                    return (String) arg;
                } else if (arg instanceof IdempotentDetail) {
                    return ((IdempotentDetail) arg).getIdempotencyKey();
                } else {
                    throw new IllegalArgumentException(
                        "Parameter annotated with @IdempotentKey must be String or implement IdempotentMessage");
                }
            }
        }
        throw new IllegalStateException("No parameter annotated with @IdempotentKey found in method " + method.getName());
    }
}
