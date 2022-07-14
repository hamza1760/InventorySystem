package com.inventory.system.InventorySystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.system.InventorySystem.constant.Constants;
import com.inventory.system.InventorySystem.entities.Warehouse;

public class AddressDto {

    private int addressId;
    private long postalCode;
    private String areaName;
    private String street;
    @JsonIgnore
    private String status = Constants.ACTIVE.getValue();
    private CityDetailDto city;
    @JsonIgnore
    private WarehouseDto warehouse;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CityDetailDto getCity() {
        return city;
    }

    public void setCity(CityDetailDto city) {
        this.city = city;
    }

    public WarehouseDto getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseDto warehouse) {
        this.warehouse = warehouse;
    }
}
