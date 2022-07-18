package com.inventory.system.InventorySystem.dto;

import com.fasterxml.jackson.annotation.*;
import com.inventory.system.InventorySystem.constant.*;

import java.util.*;

public class WarehouseDto {
    private int warehouseId;
    private String warehouseName;
    @JsonIgnore
    private String status = Constants.ACTIVE.getValue();
    private AddressDto address;

    public WarehouseDto() {
    }

    public WarehouseDto(int warehouseId, String warehouseName, String status) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.status = status;
    }

    private Set<InventoryDetailDto> inventory = new HashSet<>();

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public Set<InventoryDetailDto> getInventory() {
        return inventory;
    }

    public void setInventory(Set<InventoryDetailDto> inventory) {
        this.inventory = inventory;
    }
}
