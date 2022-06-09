package com.inventory.system.InventorySystem.api.response;


public class ApiResponseCountry {


    String message;
    String countryCode;

    public ApiResponseCountry(String message, String countryCode) {
        this.message = message;
        this.countryCode = countryCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}


