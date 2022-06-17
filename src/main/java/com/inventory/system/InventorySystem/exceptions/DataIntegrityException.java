package com.inventory.system.InventorySystem.exceptions;

public class DataIntegrityException extends RuntimeException {

    String msg;
    int id;

    public DataIntegrityException(String msg, int id) {
        this.msg = msg;
        this.id = id;
    }
}
