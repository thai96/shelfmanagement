package com.thai.pham.inventoryservice.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.thai.pham.inventoryservice.models.RequestProcessState;

@Component
public class IdempotentInterceptor implements HandlerInterceptor {
    private RedisTemplate<String, RequestProcessState> redisProcessor;
    private IdempotentKeyGenerator idempotentKeyGenerator;
    private static final String IDEMPOTENCY_HEADER = "Idempotency-Key";
    private static final Long IDEMPOTENT_VALUE_TTL_MILLIS = 10000L;
    private static final String IDEMPOTENT_REQUEST_PROCESSING_MSG = "Request is currently being processed. Please wait.";

    @Autowired
    public IdempotentInterceptor(
        @Qualifier("requestProcessState") RedisTemplate<String, RequestProcessState> redisProcessor,
        IdempotentKeyGenerator idempotentKeyGenerator) {
        this.redisProcessor = redisProcessor;
        this.idempotentKeyGenerator = idempotentKeyGenerator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception  {
        String key = request.getHeader(IDEMPOTENCY_HEADER);

        if(key == null || key.isBlank()) {
            return true;
        }
        String idempotentKey = idempotentKeyGenerator.generateKey(key);
        RequestProcessState requestProcessState = redisProcessor.opsForValue().setIfAbsent(idempotentKey, RequestProcessState.PROCESSING, Duration.ofMillis(IDEMPOTENT_VALUE_TTL_MILLIS));
        if(!RequestProcessState.COMPLETED.equals(requestProcessState)) {
            RequestProcessState currentRequestState = redisProcessor.opsForValue().get(idempotentKey);
            if(currentRequestState != null) {
                switch(currentRequestState) {
                    case RequestProcessState.PROCESSING: {
                        response.setStatus(HttpStatus.CONFLICT.value());
                        response.getWriter().write(IDEMPOTENT_REQUEST_PROCESSING_MSG);
                        return false;
                    }
                    case RequestProcessState.COMPLETED: {
                        response.setStatus(HttpStatus.OK.value());
                        // response.getWriter().write(IDEMPOTENT_REQUEST_PROCESSING_MSG);
                        return false;
                    }
                }
            }
        }

        return true;
    }
}