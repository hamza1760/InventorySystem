package com.inventory.system.InventorySystem.exceptions;

public class GlobalException extends RuntimeException {

    String message;
    int id;

    public GlobalException(String message, int id) {
        this.message = message;
        this.id = id;
    }
}
