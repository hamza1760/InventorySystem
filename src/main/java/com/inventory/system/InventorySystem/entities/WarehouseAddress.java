package com.inventory.system.InventorySystem.entities;

public class WarehouseAddress {

    private String countryName;
    private String cityName;
    private String areaName;
    private String warehouseName;

    public WarehouseAddress(String countryName, String cityName, String areaName, String warehouseName) {
        this.countryName = countryName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.warehouseName = warehouseName;
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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}






