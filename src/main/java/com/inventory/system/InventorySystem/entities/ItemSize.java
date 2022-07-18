package com.inventory.system.InventorySystem.entities;

import java.util.*;

public class ItemSize {
    private int inventoryId;
    private int itemId;
    private String itemSize;
    private String itemName;
    private String itemType;
    private String productType;
    private String brandName;

    public ItemSize() {
    }

    public ItemSize(int inventoryId, int itemId, String itemSize, String itemName, String itemType, String productType, String brandName) {
        this.inventoryId = inventoryId;
        this.itemId = itemId;
        this.itemSize = itemSize;
        this.itemName = itemName;
        this.itemType = itemType;
        this.productType = productType;
        this.brandName = brandName;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemSize itemSize1 = (ItemSize) o;
        return inventoryId == itemSize1.inventoryId && itemId == itemSize1.itemId && Objects.equals(itemSize, itemSize1.itemSize) && Objects.equals(itemName, itemSize1.itemName) && Objects.equals(itemType, itemSize1.itemType) && Objects.equals(productType, itemSize1.productType) && Objects.equals(brandName, itemSize1.brandName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryId, itemId, itemSize, itemName, itemType, productType, brandName);
    }
}

