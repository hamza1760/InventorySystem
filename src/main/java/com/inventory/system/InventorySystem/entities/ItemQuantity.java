package com.inventory.system.InventorySystem.entities;

public class ItemQuantity {
    private String warehouseName;
    private String areaName;
    private String cityName;
    private String countryName;
    private int inventoryId;
    private String itemSize;
    private int inStock;
    private int avlQty;
    private String itemName;
    private String itemColor;
    private String itemType;
    private String productType;
    private String brandName;

    public ItemQuantity(String warehouseName, String areaName, String cityName, String countryName, int inventoryId, String itemSize, int inStock, int avlQty, String itemName, String itemColor, String itemType, String productType, String brandName) {
        this.warehouseName = warehouseName;
        this.areaName = areaName;
        this.cityName = cityName;
        this.countryName = countryName;
        this.inventoryId = inventoryId;
        this.itemSize = itemSize;
        this.inStock = inStock;
        this.avlQty = avlQty;
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.itemType = itemType;
        this.productType = productType;
        this.brandName = brandName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getAvlQty() {
        return avlQty;
    }

    public void setAvlQty(int avlQty) {
        this.avlQty = avlQty;
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
