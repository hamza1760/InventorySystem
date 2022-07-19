package com.inventory.system.InventorySystem.entities;

import com.inventory.system.InventorySystem.constant.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Proxy(lazy = false)
public class ItemType {

    @Id
    @Positive
    private int itemTypeId;
    @NotEmpty
    private String itemType;
    private String status = Constants.ACTIVE.getValue();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "itemType")
    private final Set<InventoryDetail> inventory = new HashSet<>();

    public ItemType() {
    }

    public ItemType(int itemTypeId, String itemType, String status) {
        this.itemTypeId = itemTypeId;
        this.itemType = itemType;
        this.status = status;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<InventoryDetail> getInventory() {
        return inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemType itemType1 = (ItemType) o;
        return itemTypeId == itemType1.itemTypeId && Objects.equals(itemType, itemType1.itemType) && Objects.equals(status, itemType1.status) && Objects.equals(inventory, itemType1.inventory);
    }
}
