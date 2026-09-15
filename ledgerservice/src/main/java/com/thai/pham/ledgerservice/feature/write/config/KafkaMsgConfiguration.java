package com.thai.pham.ledgerservice.feature.write.config;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration
public class KafkaMsgConfiguration {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageEnvelop>
    orderKafkaListenerContainerFactory(
            ConsumerFactory<String, MessageEnvelop> consumerFactory,
            CommonErrorHandler kafkaErrorHandler,
            @Value("${app.message.concurrent}")Integer concurrentConsumer
    ) {

        var factory =
                new ConcurrentKafkaListenerContainerFactory<String, MessageEnvelop>();

        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(concurrentConsumer);
        factory.setCommonErrorHandler(kafkaErrorHandler);

        return factory;
    }

    @Bean
    public DefaultErrorHandler kafkaErrorHandler(DeadLetterPublishingRecoverer recoverer) {
        ExponentialBackOff backOff = new ExponentialBackOff(2_000L, 2);

        DefaultErrorHandler handler = new DefaultErrorHandler(recoverer, backOff);

        handler.addNotRetryableExceptions(
            ConstraintViolationException.class,
            NonRetryableException.class
        );

        return handler;
    }

    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
    
        return new DeadLetterPublishingRecoverer(
                kafkaTemplate,
                (record, exception) ->
                        new TopicPartition(
                                record.topic() + ".DLT",
                                record.partition()
                        )
        );
    }
}