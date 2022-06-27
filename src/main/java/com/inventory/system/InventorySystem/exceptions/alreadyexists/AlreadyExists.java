package com.inventory.system.InventorySystem.exceptions.alreadyexists;

import com.inventory.system.InventorySystem.constant.alreadyexists.AlreadyExistsConstant;

public class AlreadyExists extends RuntimeException {


    public AlreadyExistsConstant message;
    public int id;

    public AlreadyExists(AlreadyExistsConstant message, int id) {
        this.message = message;
        this.id = id;
    }
}
