package com.inventory.system.InventorySystem.entities;

import com.inventory.system.InventorySystem.constant.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.*;

@Entity
@Proxy(lazy = false)
public class Warehouse {

    @Id
    private int warehouseId;
    private String warehouseName;
    private String status = Constants.ACTIVE.getValue();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id_fk")
    private Address address;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return warehouseId == warehouse.warehouseId && Objects.equals(warehouseName, warehouse.warehouseName) && Objects.equals(status, warehouse.status);
    }
}
