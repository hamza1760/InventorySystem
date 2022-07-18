package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.*;
import com.inventory.system.InventorySystem.constant.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Proxy(lazy = false)
public class Warehouse {

    @Id
    @Positive
    private int warehouseId;
    @NotEmpty
    private String warehouseName;
    private String status = Constants.ACTIVE.getValue();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id_fk")
    private Address address;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "warehouse")
    private final Set<InventoryDetail> inventory = new HashSet<>();

    public Warehouse() {
    }

    public Warehouse(int warehouseId, String warehouseName, String status) {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInventory(InventoryDetail inventory) {
        this.inventory.add(inventory);
    }

    public Set<InventoryDetail> getInventory() {
        return inventory;
    }
}
