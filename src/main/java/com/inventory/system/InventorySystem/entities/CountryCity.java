package com.inventory.system.InventorySystem.entities;

public class CountryCity {

    private String countryName;
    private String cityName;
    private String areaName;

    public CountryCity(String countryName, String cityName, String areaName) {
        this.countryName = countryName;
        this.cityName = cityName;
        this.areaName = areaName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}





