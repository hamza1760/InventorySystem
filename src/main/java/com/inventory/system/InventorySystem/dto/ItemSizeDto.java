package com.inventory.system.InventorySystem.dto;

public class ItemSizeDto {
    private int inventoryId;
    private int itemId;
    private String itemSize;
    private String itemName;
    private String itemType;
    private String productType;
    private String brandName;

    public ItemSizeDto(int inventoryId, int itemId, String itemSize, String itemName, String itemType, String productType, String brandName) {
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
}
