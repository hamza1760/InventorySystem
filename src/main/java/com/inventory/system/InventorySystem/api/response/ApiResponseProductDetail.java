package com.inventory.system.InventorySystem.api.response;


public class ApiResponseProductDetail {

    String message;
    int productId;

    public ApiResponseProductDetail(String message, int productId) {
        this.message = message;
        this.productId = productId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
