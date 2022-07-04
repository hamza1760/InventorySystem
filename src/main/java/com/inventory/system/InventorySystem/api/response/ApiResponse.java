package com.inventory.system.InventorySystem.api.response;

public class ApiResponse {

    String message;
    int id;

    public ApiResponse(String message, int id) {
        this.message = message;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
