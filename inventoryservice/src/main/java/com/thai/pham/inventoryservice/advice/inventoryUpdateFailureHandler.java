package com.thai.pham.inventoryservice.advice;

@RestControllerAdvice(assignableTypes = InventoryController.class)
public class LockFailureHandler {
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handlerConflict(ObjectOptimisticLockingFailureException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "Update conflict detected. Please refresh and apply your changes again.");
        error.put("entity", ex.getPersistentClass().getSimpleName());
        error.put("id", String.valueOf(ex.getIdentifier()));
        return error;
    }
}