package com.inventory.system.InventorySystem.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.system.InventorySystem.entities.InventoryDetail;
import com.inventory.system.InventorySystem.entities.Warehouse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemDto {

    private int itemId;
    private String itemName;
    private String itemColor;

    private Set<InventoryDetail> inventoryDetail = new HashSet<>();

    @JsonIgnore
    private List<Warehouse> warehouse;

    public ItemDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ItemDto(int itemId, String itemName, String itemColor) {
        super();
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemColor = itemColor;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public List<Warehouse> getWarehouse() {
        return warehouse;
    }

    public Set<InventoryDetail> getInventoryDetail() {
        return inventoryDetail;
    }
}
