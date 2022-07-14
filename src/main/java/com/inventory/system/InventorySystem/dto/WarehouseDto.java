package com.inventory.system.InventorySystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.system.InventorySystem.constant.Constants;
import com.inventory.system.InventorySystem.entities.Address;
import com.inventory.system.InventorySystem.entities.InventoryDetail;

import java.util.HashSet;
import java.util.Set;

public class WarehouseDto {
    private int warehouseId;
    private String warehouseName;
    @JsonIgnore
    private String status = Constants.ACTIVE.getValue();
    private Address address;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<InventoryDetailDto> getInventory() {
        return inventory;
    }

    public void setInventory(Set<InventoryDetailDto> inventory) {
        this.inventory = inventory;
    }
}
