package com.inventory.system.InventorySystem.dto;

import com.fasterxml.jackson.annotation.*;
import com.inventory.system.InventorySystem.constant.*;

import java.util.*;

public class ItemDto {

    private int itemId;
    private String itemName;
    @JsonIgnore
    private String status = Constants.ACTIVE.getValue();
    private ProductTypeDto productType;
    private BrandDetailDto brand;
    @JsonIgnore
    private Set<InventoryDetailDto> inventory = new HashSet<>();

    public ItemDto() {
    }

    public ItemDto(int itemId, String itemName, String status, BrandDetailDto brand, ProductTypeDto productType) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.status = status;
        this.productType = productType;
        this.brand = brand;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProductTypeDto getProductType() {
        return productType;
    }

    public void setProductType(ProductTypeDto productType) {
        this.productType = productType;
    }

    public BrandDetailDto getBrand() {
        return brand;
    }

    public void setBrand(BrandDetailDto brand) {
        this.brand = brand;
    }

    public Set<InventoryDetailDto> getInventory() {
        return inventory;
    }

    public void setInventory(Set<InventoryDetailDto> inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", status='" + status + '\'' +
                ", productType=" + productType +
                ", brand=" + brand +
                ", inventory=" + inventory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return itemId == itemDto.itemId && Objects.equals(itemName, itemDto.itemName) && Objects.equals(status, itemDto.status) && Objects.equals(productType, itemDto.productType) && Objects.equals(brand, itemDto.brand) && Objects.equals(inventory, itemDto.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, status, productType, brand, inventory);
    }
}


