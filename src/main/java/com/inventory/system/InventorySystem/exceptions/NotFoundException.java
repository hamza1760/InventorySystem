package com.inventory.system.InventorySystem.exceptions;

import com.inventory.system.InventorySystem.constant.Constants;

public class NotFoundException extends RuntimeException {

    public Constants message;
    public int id;

    public NotFoundException(Constants message, int id) {
        this.message = message;
        this.id = id;
    }
}
