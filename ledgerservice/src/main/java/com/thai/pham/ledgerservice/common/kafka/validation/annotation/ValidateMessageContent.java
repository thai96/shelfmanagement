package com.thai.pham.ledgerservice.common.kafka.validation.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateMessageContent {
}
