package com.inventory.system.InventorySystem.dto;

import com.fasterxml.jackson.annotation.*;
import com.inventory.system.InventorySystem.constant.*;
import org.hibernate.annotations.*;
import org.springframework.data.repository.cdi.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

public class WarehouseDto {

    @Positive
    private int warehouseId;
    @NotEmpty
    private String warehouseName;
    @JsonIgnore
    private String status = Constants.ACTIVE.getValue();
    private AddressDto address;

    private Set<InventoryDetailDto> inventory;

    public WarehouseDto() {
    }

    public WarehouseDto(int warehouseId, String warehouseName, String status) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.status = status;
    }

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
