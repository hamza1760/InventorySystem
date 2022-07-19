package com.inventory.system.InventorySystem.dto;

import com.fasterxml.jackson.annotation.*;
import com.inventory.system.InventorySystem.constant.*;

import java.util.*;

public class ItemTypeDto {

    private int itemTypeId;
    private String itemType;
    @JsonIgnore
    private String status = Constants.ACTIVE.getValue();
    @JsonIgnore
    private final Set<InventoryDetailDto> inventory = new HashSet<>();

    public ItemTypeDto() {
    }

    public ItemTypeDto(int itemTypeId, String itemType, String status) {
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

    public Set<InventoryDetailDto> getInventory() {
        return inventory;
    }
}
