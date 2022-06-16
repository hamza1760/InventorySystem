package com.inventory.system.InventorySystem.entities;

public class ItemSize {
    private String itemName;
    private String itemSize;
    private String itemType;
    private String productType;
    private String brandName;

    public ItemSize(String itemName, String itemSize, String itemType, String productType, String brandName) {
        this.itemName = itemName;
        this.itemSize = itemSize;
        this.itemType = itemType;
        this.productType = productType;
        this.brandName = brandName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
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
