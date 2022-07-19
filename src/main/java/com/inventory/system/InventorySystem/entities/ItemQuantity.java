package com.inventory.system.InventorySystem.entities;

import java.util.*;

public class ItemQuantity {

    private int warehouseId;
    private String warehouseName;
    private String areaName;
    private String cityName;
    private String countryName;
    private int inventoryId;
    private String itemSize;
    private int inStock;
    private int avlQty;
    private String itemName;
    private int itemId;
    private String itemType;
    private String productType;
    private String brandName;

    public ItemQuantity() {
    }

    public ItemQuantity(int warehouseId, String warehouseName, String areaName, String cityName, String countryName, int inventoryId, String itemSize, int inStock, int avlQty, String itemName, int itemId, String itemType, String productType, String brandName) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.areaName = areaName;
        this.cityName = cityName;
        this.countryName = countryName;
        this.inventoryId = inventoryId;
        this.itemSize = itemSize;
        this.inStock = inStock;
        this.avlQty = avlQty;
        this.itemName = itemName;
        this.itemId = itemId;
        this.itemType = itemType;
        this.productType = productType;
        this.brandName = brandName;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
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

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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
        ItemQuantity that = (ItemQuantity) o;
        return warehouseId == that.warehouseId && inventoryId == that.inventoryId && inStock == that.inStock && avlQty == that.avlQty && itemId == that.itemId && Objects.equals(warehouseName, that.warehouseName) && Objects.equals(areaName, that.areaName) && Objects.equals(cityName, that.cityName) && Objects.equals(countryName, that.countryName) && Objects.equals(itemSize, that.itemSize) && Objects.equals(itemName, that.itemName) && Objects.equals(itemType, that.itemType) && Objects.equals(productType, that.productType) && Objects.equals(brandName, that.brandName);
    }
}
