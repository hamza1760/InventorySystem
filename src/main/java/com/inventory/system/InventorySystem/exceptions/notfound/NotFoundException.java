package com.inventory.system.InventorySystem.exceptions.notfound;

import com.inventory.system.InventorySystem.constant.notfound.NotFoundConstant;

public class NotFoundException extends RuntimeException {


    public NotFoundConstant message;
    public int id;

    public NotFoundException(NotFoundConstant message, int id) {
        this.message = message;
        this.id = id;
    }

    public void Throwable() {
        fillInStackTrace();
    }
}
