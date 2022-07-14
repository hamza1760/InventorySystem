package com.inventory.system.InventorySystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.system.InventorySystem.constant.Constants;

import java.util.HashSet;
import java.util.Set;

public class ItemTypeDto {
    private int itemTypeId;
    private String itemType;
    @JsonIgnore
    private final String status = Constants.ACTIVE.getValue();
    @JsonIgnore
    private final Set<InventoryDetailDto> inventory = new HashSet<>();

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

    public Set<InventoryDetailDto> getInventory() {
        return inventory;
    }
}
