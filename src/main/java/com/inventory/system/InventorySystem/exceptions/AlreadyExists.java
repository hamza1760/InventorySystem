package com.inventory.system.InventorySystem.exceptions;

import com.inventory.system.InventorySystem.constant.Constants;

public class AlreadyExists extends RuntimeException {

    public Constants message;
    public int id;

    public AlreadyExists(Constants message, int id) {
        this.message = message;
        this.id = id;
    }
}
