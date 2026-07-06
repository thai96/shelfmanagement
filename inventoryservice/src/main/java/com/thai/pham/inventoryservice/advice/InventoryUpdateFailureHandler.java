package com.thai.pham.inventoryservice.advice;

import com.thai.pham.inventoryservice.controller.InventoryController;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(assignableTypes = InventoryController.class)
public class InventoryUpdateFailureHandler {
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handlerConflict(ObjectOptimisticLockingFailureException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "Update conflict detected. Please refresh and apply your changes again.");
        error.put("entity", ex.getPersistentClass().getSimpleName());
        error.put("id", String.valueOf(ex.getIdentifier()));
        return error;
    }
}