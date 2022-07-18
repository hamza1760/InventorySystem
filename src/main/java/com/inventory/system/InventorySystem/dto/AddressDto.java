package com.inventory.system.InventorySystem.dto;

import com.fasterxml.jackson.annotation.*;
import com.inventory.system.InventorySystem.constant.*;

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

    public AddressDto() {
    }

    public AddressDto(int addressId, long postalCode, String areaName, String street, String status) {
        this.addressId = addressId;
        this.postalCode = postalCode;
        this.areaName = areaName;
        this.street = street;
        this.status = status;
    }

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
