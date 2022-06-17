package com.inventory.system.InventorySystem.api.response;


public class ApiResponseDataIntegrity {

    String message;
    int id;

    public ApiResponseDataIntegrity(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

