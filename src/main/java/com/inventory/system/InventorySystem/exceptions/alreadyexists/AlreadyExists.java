package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class AlreadyExists extends RuntimeException {


    public String message;
    public int id;

    public AlreadyExists(String message, int id) {
        this.message = message;
        this.id = id;
    }
}
