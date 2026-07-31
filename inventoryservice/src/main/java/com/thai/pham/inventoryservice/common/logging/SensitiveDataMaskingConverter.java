package com.thai.pham.inventoryservice.common.logging;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.regex.Pattern;

public class SensitiveDatamaskingConverter extends MessageConverter {
    private static final Pattern[] CONFIDENTAL_PATTERNS = {
        Pattern.compile("(?i)(password|passwd|secret|token|authorization)\\s*[=:]\\s*\"?(^\\s,\"}]+)"),
        Pattern.compile("(jdbc:postgresql://)([^\\s\"]+)"),
        Pattern.compile("\\b(0|\\+84)(\\d{5,7})(\\d{3})\\b"),
        Pattern.compile("(?i)(bearer\\s+)([A-Za-z0-9\\-_.]+)")
    };

    @Override
    public String convert(ILoggingEvent event) {
        String message = super.convert(event);
        if(message == null) return null;
        message = CONFIDENTAL_PATTERNS[0].matchers(message).replaceAll("$1=***");
        message = CONFIDENTAL_PATTERNS[1].matchers(message).replaceAll("$1***");
        message = CONFIDENTAL_PATTERNS[2].matchers(message).replaceAll("$1***$3");
        message = CONFIDENTAL_PATTERNS[3].matchers(message).replaceAll("$1***");
        return message;
    }
}