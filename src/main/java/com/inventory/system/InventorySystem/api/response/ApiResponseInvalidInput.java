package com.inventory.system.InventorySystem.api.response;


public class ApiResponseInvalidInput {


    String message;

    public ApiResponseInvalidInput(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


