package com.inventory.system.InventorySystem.entities;

public class ItemSize {
    private int inventoryId;
    private String itemSize;
    private String itemName;
    private String itemType;
    private String productType;
    private String brandName;

    public ItemSize(int inventoryId, String itemSize, String itemName, String itemType, String productType, String brandName) {
        this.inventoryId = inventoryId;
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
}

