package com.inventory.system.InventorySystem.api.response;


public class ApiResponseCountry {


    String message;
    int countryId;

    public ApiResponseCountry(String message, int countryId) {
        this.message = message;
        this.countryId = countryId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}


