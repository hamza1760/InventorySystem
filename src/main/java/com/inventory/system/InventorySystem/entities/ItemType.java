package com.inventory.system.InventorySystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.system.InventorySystem.constant.Constants;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

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

    public ItemType(String status, int itemTypeId, String itemType) {
        super();
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
}
